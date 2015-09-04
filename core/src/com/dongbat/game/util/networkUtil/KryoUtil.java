/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.networkUtil;

import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.dongbat.game.component.BuffComponent;
import com.dongbat.game.component.Display;
import com.dongbat.game.component.Physics;
import com.dongbat.game.util.box2d.serializer.BodyState;
import com.dongbat.game.util.box2d.serializer.Box2dState;
import com.dongbat.game.util.box2d.serializer.FixtureState;
import com.dongbat.game.util.msg.WorldState;
import com.dongbat.game.util.object.EntityState;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import com.esotericsoftware.kryo.serializers.DeflateSerializer;
import java.io.ByteArrayInputStream;
import java.util.UUID;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 *
 * @author Admin
 */
public class KryoUtil {

  private static Kryo kryo;

  public static Kryo getKryo() {
    if (kryo == null) {
      kryo = newKryo();
    }
    return kryo;
  }

  public static Kryo newKryo() {
    kryo = new Kryo();
    kryo.setAsmEnabled(true);
    kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
    kryo.register(Body.class, new DefaultSerializers.VoidSerializer());
    kryo.register(Entity.class, new DefaultSerializers.VoidSerializer());
    kryo.register(Display.class, new DefaultSerializers.VoidSerializer());
    kryo.register(Physics.class, new DefaultSerializers.VoidSerializer());

    kryo.register(BodyState.class);
    kryo.register(BodyState.class, new DeflateSerializer(kryo.getSerializer(BodyState.class)));

    kryo.register(FixtureState.class);
    kryo.register(FixtureState.class, new DeflateSerializer(kryo.getSerializer(FixtureState.class)));
    kryo.register(FixtureState.class);
    kryo.register(FixtureState.class, new DeflateSerializer(kryo.getSerializer(FixtureState.class)));
    kryo.register(BuffComponent.class);
    kryo.register(BuffComponent.class, new DeflateSerializer(kryo.getSerializer(BuffComponent.class)));
    kryo.register(Box2dState.class);
    kryo.register(Box2dState.class, new DeflateSerializer(kryo.getSerializer(Box2dState.class)));
    kryo.register(UUID.class);
    kryo.register(UUID.class, new DeflateSerializer(kryo.getSerializer(UUID.class)));
    kryo.register(EntityState.class);
    kryo.register(EntityState.class, new DeflateSerializer(kryo.getSerializer(EntityState.class)));
    kryo.register(WorldState.class);
    kryo.register(WorldState.class, new DeflateSerializer(kryo.getSerializer(WorldState.class)));

    return kryo;
  }

  public static <T> byte[] serialize(T data, Class<T> type) {
    Output output = new Output(2048, -1);
    getKryo().writeObjectOrNull(output, data, type);
    byte[] buffer = output.getBuffer();
    output.close();
    return buffer;
  }

  public static <T> T deserialize(byte[] data, Class<T> type) {
    Input input = new Input(new ByteArrayInputStream(data));
    T value = getKryo().readObjectOrNull(input, type);
    input.close();
    return value;
  }
}
