package com.dongbat.game.util.networkUtil.network;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.component.Player;
import com.dongbat.game.dataobject.CustomInput;
import com.dongbat.game.util.ECSUtil;
import static com.dongbat.game.util.ECSUtil.getStep;
import static com.dongbat.game.util.ECSUtil.normalProcess;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.msg.InitState;
import com.dongbat.game.util.msg.NetworkInput;
import com.dongbat.game.util.msg.WorldState;
import static com.dongbat.game.util.networkUtil.WorldStateUtil.rewindAndReplay;
import static com.dongbat.game.util.networkUtil.WorldStateUtil.save;
import com.dongbat.game.util.object.CustomWorld;
import com.dongbat.game.util.object.WorldProgress;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.util.UUID;

/**
 *
 * @author tao
 */
public class GameServer extends Listener {

  private World world;
  private final long inputTimeout;
  private final Server server;
  private final ObjectMap<UUID, Connection> idToConnection = new ObjectMap<UUID, Connection>();
  private final ObjectMap<Connection, UUID> connectionToId = new ObjectMap<Connection, UUID>();
  private boolean needRewind = false;

  public GameServer(Server server, long inputTimeout) {
    this.inputTimeout = inputTimeout;
    this.server = server;

    server.addListener(this);

  }

  public void startGame(CustomWorld world) {
    this.world = world;
    PhysicsUtil.init(world);
//    TiledMap tiledMap = LevelLoader.init(world, false);
    Connection[] connections = server.getConnections();

    for (Connection connection : connections) {
//      UUID id = LevelLoader.createPlayer(world, tiledMap);
      ///Fixx TODO
      UUID id = null;
      idToConnection.put(id, connection);
      connectionToId.put(connection, id);
    }

    ECSUtil.updateEntityStates(world);

    baseState = save(world);

    for (Connection connection : connections) {
      server.sendToUDP(connection.getID(), new InitState(connectionToId.get(connection), baseState));
    }
  }

  public World getWorld() {
    return world;
  }

  private Array<NetworkInput> unprocessedInputs = new Array<NetworkInput>();
  private WorldState baseState;
  private WorldState nextBaseState;

  private ObjectMap<String, Connection> nameToConnection = new ObjectMap<String, Connection>();
  private ObjectMap<Connection, String> connectionToName = new ObjectMap<Connection, String>();

  public Array<String> getPlayerNames() {
    Array<String> playerNames = new Array<String>();
    for (ObjectMap.Entry<Connection, String> entry : connectionToName) {
      playerNames.add(entry.value);
    }
    return playerNames;
  }

  @Override
  public void disconnected(Connection connection) {

    // TODO: update id-connection map, set null
  }

  @Override
  public void received(Connection connection, Object object) {
    if (object instanceof NetworkInput) {
      // handle input, add to unprocessed, broadcast
      NetworkInput input = (NetworkInput) object;
      unprocessedInputs.add(input);
      server.sendToAllUDP(input);
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
    }

    // handle reconnect, receive uuid, update connection-id map
  }

  public void process(float delta) {

    long targetFrame = ECSUtil.getFrame(world);
    float step = getStep(world);

    // rewind (load base state) + replay (process unprocessedInputs), save next base state (% inputTimeout == 0)
    if (needRewind) {
      // rewind
      rewindAndReplay(world, baseState, unprocessedInputs, targetFrame, step);
      needRewind = false;
    }

    WorldProgress worldProgress = ECSUtil.getWorldProgress(world);

    worldProgress.increaseAccumulated(delta);

    while (worldProgress.getAccumulated() >= step) {
      normalProcess(world, step);
      worldProgress.increaseAccumulated(-step);
    }

    if (targetFrame - baseState.getCurrentFrame() >= inputTimeout) {
      // save and send base state on time slice
      if (nextBaseState != null) {
        baseState = nextBaseState;
      }
      if (targetFrame - inputTimeout >= baseState.getCurrentFrame()) {
        nextBaseState = save(world);
      }
      server.sendToAllUDP(baseState);

      // discard old input on time slice
      for (NetworkInput networkInput : unprocessedInputs) {
        if (networkInput.getFrameIndex() < baseState.getCurrentFrame()) {
          unprocessedInputs.removeValue(networkInput, true);
        }
      }

    }

  }

}
