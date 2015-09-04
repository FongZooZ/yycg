package com.dongbat.game.util.networkUtil.network;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.component.Player;
import com.dongbat.game.dataobject.CustomInput;
import com.dongbat.game.screen.network.NetworkGameScreen;
import com.dongbat.game.util.ECSUtil;
import static com.dongbat.game.util.ECSUtil.normalProcess;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.ScreenUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.localUtil.LocalPlayerUtil;
import com.dongbat.game.util.msg.InitState;
import com.dongbat.game.util.msg.NetworkInput;
import com.dongbat.game.util.msg.WorldState;
import com.dongbat.game.util.networkUtil.NetworkUtil;
import static com.dongbat.game.util.networkUtil.WorldStateUtil.load;
import static com.dongbat.game.util.networkUtil.WorldStateUtil.rewindAndReplay;
import com.dongbat.game.util.object.WorldProgress;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.util.UUID;

/**
 *
 * @author tao
 */
public class GameClient extends Listener {

  private World world;
  private Client client;
  private WorldState baseState;
  private Array<NetworkInput> unprocessedInputs = new Array<NetworkInput>();
  private boolean needRewind;

  public GameClient(Client client) {
    this.client = client;
    client.addListener(this);
    world = ECSUtil.createWorld();
  }

  public World getWorld() {
    return world;
  }

  public void setWorld(World world) {
    this.world = world;
  }

  public void process(float delta) {
    long targetFrame = ECSUtil.getFrame(world);
    float step = ECSUtil.getStep(world);

    // TODO: do this in a time slice
    if (needRewind) {
      // rewind
      rewindAndReplay(world, baseState, unprocessedInputs, targetFrame, step);
      needRewind = false;
    }

    if (targetFrame % 100 == 0) {
      // TODO: make use of this
      client.updateReturnTripTime();
    }

    WorldProgress worldProgress = ECSUtil.getWorldProgress(world);

    worldProgress.increaseAccumulated(delta);

    while (worldProgress.getAccumulated() >= step) {
      normalProcess(world, step);
      worldProgress.increaseAccumulated(-step);
    }

    ECSUtil.processPassive(world, delta);

    // move this to base state received
    for (NetworkInput networkInput : unprocessedInputs) {
      if (networkInput.getFrameIndex() < baseState.getCurrentFrame()) {
        unprocessedInputs.removeValue(networkInput, true);
      }
    }
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  @Override
  public void received(Connection connection, Object object) {
    // post runnable

    if (object instanceof InitState) {
      InitState initState = (InitState) object;
      LocalPlayerUtil.setLocalPlayer(initState.getClientId());

      baseState = initState.getBaseState();
      load(world, initState.getBaseState(), true);

      // delay start for frame syncing
      if (!NetworkUtil.isInGame()) {
        NetworkUtil.setInGame(true);
        ScreenUtil.setScreen(new NetworkGameScreen(this));
      }
    } else if (object instanceof NetworkInput) {
      NetworkInput input = (NetworkInput) object;
      if (input.getFrameIndex() < ECSUtil.getFrame(world)) {
        unprocessedInputs.add(input);
        needRewind = true;
      } else {
        unprocessedInputs.add(input);
        UUID id = input.getId();

        CustomInput customInput = input.getInput();
        Entity e = UuidUtil.getEntityByUuid(world, id);
        EntityUtil.getComponent(world, e, Player.class).getInputs().put(input.getFrameIndex(), customInput);
      }
    } else if (object instanceof WorldState) {
      baseState = (WorldState) object;
      needRewind = true;
    }
  }

}
