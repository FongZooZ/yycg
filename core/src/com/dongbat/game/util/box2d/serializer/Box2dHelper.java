package com.dongbat.game.util.box2d.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.badlogic.gdx.utils.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author tao
 */
public class Box2dHelper {

  protected Map<Integer, Body> m_indexToBodyMap;
  protected Map<Body, Integer> m_bodyToIndexMap;
  protected Map<Joint, Integer> m_jointToIndexMap;
  protected Stack<Body> m_bodies;
  protected Stack<Joint> m_joints;

  public Box2dHelper() {
    m_indexToBodyMap = new HashMap<Integer, Body>();
    m_bodyToIndexMap = new HashMap<Body, Integer>();
    m_jointToIndexMap = new HashMap<Joint, Integer>();

    m_bodies = new Stack<Body>();
    m_joints = new Stack<Joint>();

  }

  public BodyState fromBody(Body body) {
    BodyState bodyState = new BodyState();

    switch (body.getType()) {
      case StaticBody:
        bodyState.setType(0);
        break;
      case KinematicBody:
        bodyState.setType(1);
        break;
      case DynamicBody:
        bodyState.setType(2);
        break;
    }

    bodyState.setPosition(body.getPosition());
    bodyState.setAngle(body.getAngle());
    bodyState.setUserData(body.getUserData());

    bodyState.setLinearVelocity(body.getLinearVelocity());
    bodyState.setAngularVelocity(body.getAngularVelocity());

    bodyState.setLinearDamping(body.getLinearDamping());
    bodyState.setAngularDamping(body.getAngularDamping());
    bodyState.setGravityScale(body.getGravityScale());

    bodyState.setBullet(body.isBullet());
    bodyState.setAllowSleep(body.isSleepingAllowed());
    bodyState.setAwake(body.isAwake());
    bodyState.setActive(body.isActive());
    bodyState.setFixedRotation(body.isFixedRotation());

    MassData massData = body.getMassData();
    bodyState.setMassDataMass(massData.mass);
    bodyState.setMassDataCenter(massData.center);
    bodyState.setMassDataI(massData.I);

    bodyState.setFixture(new Array<FixtureState>());

    for (Fixture fixture : body.getFixtureList()) {
      FixtureState fixtureState = fromFixture(fixture);
      bodyState.getFixture().add(fixtureState);
    }

    return bodyState;
  }

  public JointState fromJoint(Joint joint) {
    JointState jointState = new JointState();

    int bodyIndexA = m_bodyToIndexMap.get(joint.getBodyA());
    int bodyIndexB = m_bodyToIndexMap.get(joint.getBodyB());
    jointState.setBodyA(bodyIndexA);
    jointState.setBodyB(bodyIndexB);
    jointState.setCollideConnected(joint.getCollideConnected());

    Body bodyA = joint.getBodyA();
    Body bodyB = joint.getBodyB();

    jointState.setUserData(joint.getUserData());

    Vector2 tmpAnchor;

    switch (joint.getType()) {
      case RevoluteJoint: {
        jointState.setType("revolute");

        RevoluteJoint revoluteJoint = (RevoluteJoint) joint;
        tmpAnchor = revoluteJoint.getAnchorA();
        jointState.setAnchorA(bodyA.getLocalPoint(tmpAnchor));

        tmpAnchor = revoluteJoint.getAnchorB();
        jointState.setAnchorB(bodyB.getLocalPoint(tmpAnchor));

        jointState.setRefAngle(bodyB.getAngle() - bodyA.getAngle() - revoluteJoint.getJointAngle());
        jointState.setJointSpeed(revoluteJoint.getJointSpeed());
        jointState.setEnableLimit(revoluteJoint.isLimitEnabled());
        jointState.setLowerLimit(revoluteJoint.getLowerLimit());
        jointState.setUpperLimit(revoluteJoint.getUpperLimit());
        jointState.setEnableMotor(revoluteJoint.isMotorEnabled());
        jointState.setMotorSpeed(revoluteJoint.getMotorSpeed());
        jointState.setMaxMotorTorque(revoluteJoint.getMaxMotorTorque());
      }
      break;
      case PrismaticJoint: {
        jointState.setType("prismatic");

        PrismaticJoint prismaticJoint = (PrismaticJoint) joint;
        tmpAnchor = prismaticJoint.getAnchorA();
        jointState.setAnchorA(bodyA.getLocalPoint(tmpAnchor));
        tmpAnchor = prismaticJoint.getAnchorB();
        jointState.setAnchorB(bodyB.getLocalPoint(tmpAnchor));

        jointState.setEnableLimit(prismaticJoint.isLimitEnabled());
        jointState.setLowerLimit(prismaticJoint.getLowerLimit());
        jointState.setUpperLimit(prismaticJoint.getUpperLimit());
        jointState.setEnableMotor(prismaticJoint.isMotorEnabled());
        jointState.setMotorSpeed(prismaticJoint.getMotorSpeed());
      }
      break;
      case DistanceJoint: {
        jointState.setType("distance");

        DistanceJoint distanceJoint = (DistanceJoint) joint;
        tmpAnchor = distanceJoint.getAnchorA();
        jointState.setAnchorA(bodyA.getLocalPoint(tmpAnchor));
        tmpAnchor = distanceJoint.getAnchorB();
        jointState.setAnchorB(bodyB.getLocalPoint(tmpAnchor));
        jointState.setLength(distanceJoint.getLength());
        jointState.setFrequency(distanceJoint.getFrequency());
        jointState.setDampingRatio(distanceJoint.getDampingRatio());
      }
      break;
      case PulleyJoint: {
        jointState.setType("pulley");

        PulleyJoint pulleyJoint = (PulleyJoint) joint;
        jointState.setGroundAnchorA(pulleyJoint.getGroundAnchorA());
        jointState.setGroundAnchorB(pulleyJoint.getGroundAnchorB());
        tmpAnchor = pulleyJoint.getAnchorA();
        jointState.setAnchorA(bodyA.getLocalPoint(tmpAnchor));
        jointState.setLengthA((pulleyJoint.getGroundAnchorA().sub(tmpAnchor)).len());
        tmpAnchor = pulleyJoint.getAnchorB();
        jointState.setAnchorB(bodyB.getLocalPoint(tmpAnchor));
        jointState.setLengthB((pulleyJoint.getGroundAnchorB().sub(tmpAnchor)).len());
        jointState.setRatio(pulleyJoint.getRatio());
      }
      break;
      case MouseJoint: {
        jointState.setType("mouse");

        MouseJoint mouseJoint = (MouseJoint) joint;
        jointState.setTarget(mouseJoint.getTarget());
        tmpAnchor = mouseJoint.getAnchorB();
        jointState.setAnchorB(tmpAnchor);
        jointState.setMaxForce(mouseJoint.getMaxForce());
        jointState.setFrequency(mouseJoint.getFrequency());
        jointState.setDampingRatio(mouseJoint.getDampingRatio());
      }
      break;
      case GearJoint: {
//        // Wheel joints are apparently not implemented in JBox2D yet, but
//        // when they are, commenting out the following section should work.
//
        jointState.setType("gear");
//        jointValue.put("type", "gear");

//        GearJoint gearJoint = (GearJoint) joint;
//        int jointIndex1
//                = lookupJointIndex(gearJoint.getJoint1());
//        int jointIndex2
//                = lookupJointIndex(gearJoint.getJoint2());
//        jointValue.put("joint1", jointIndex1);
//        jointValue.put("joint2",
//                jointIndex2);
//        jointValue.put("ratio", gearJoint.getRatio());
      }
      break;
      case WheelJoint: {
        // Wheel joints are apparently not implemented in JBox2D yet, but
        // when they are, commenting out the following section... might
        // work.
        jointState.setType("wheel");
        /*
         * jointValue.put("type", "wheel");
         *
         * WheelJoint wheelJoint = (WheelJoint)joint;
         * wheelJoint.getAnchorA(tmpAnchor); vecToJson("anchorA",
         * bodyA.getLocalPoint(tmpAnchor), jointValue);
         * wheelJoint.getAnchorB(tmpAnchor); vecToJson("anchorB",
         * bodyB.getLocalPoint(tmpAnchor), jointValue);
         * vecToJson("localAxisA", wheelJoint.getLocalAxisA(), jointValue);
         * jointValue.put("enableMotor", wheelJoint.isMotorEnabled());
         * floatToJson("motorSpeed", wheelJoint.getMotorSpeed(),
         * jointValue); floatToJson("maxMotorTorque",
         * wheelJoint.getMaxMotorTorque(), jointValue);
         * floatToJson("springFrequency", wheelJoint.getSpringFrequencyHz(),
         * jointValue); floatToJson("springDampingRatio",
         * wheelJoint.getSpringDampingRatio(), jointValue);
         */
      }
      break;
      case WeldJoint: {
        jointState.setType("weld");

        WeldJoint weldJoint = (WeldJoint) joint;
        tmpAnchor = weldJoint.getAnchorA();
        jointState.setAnchorA(bodyA.getLocalPoint(tmpAnchor));
        tmpAnchor = weldJoint.getAnchorB();
        jointState.setAnchorB(bodyB.getLocalPoint(tmpAnchor));
//        floatToJson("refAngle", weldJoint.getReferenceAngle(), jointValue);
      }
      break;
      case FrictionJoint: {
        jointState.setType("friction");

        FrictionJoint frictionJoint = (FrictionJoint) joint;
        tmpAnchor = frictionJoint.getAnchorA();
        jointState.setAnchorA(bodyA.getLocalPoint(tmpAnchor));
        tmpAnchor = frictionJoint.getAnchorB();
        jointState.setAnchorB(bodyB.getLocalPoint(tmpAnchor));
        jointState.setMaxForce(frictionJoint.getMaxForce());
        jointState.setMaxTorque(frictionJoint.getMaxTorque());
      }
      break;
      case RopeJoint: {

        jointState.setType("rope");

        RopeJoint ropeJoint = (RopeJoint) joint;
        tmpAnchor = ropeJoint.getAnchorA();
        jointState.setAnchorA(bodyA.getLocalPoint(tmpAnchor));
        tmpAnchor = ropeJoint.getAnchorB();
        jointState.setAnchorB(bodyB.getLocalPoint(tmpAnchor));
        jointState.setMaxLength(ropeJoint.getMaxLength());

      }
      break;
//      case UNKNOWN:
      default:
        System.out.println("Unknown joint type : " + joint.getType());
    }
    return jointState;
  }

  public FixtureState fromFixture(Fixture fixture) {
    FixtureState fixtureState = new FixtureState();

    fixtureState.setResitution(fixture.getRestitution());
    fixtureState.setUserData(fixture.getUserData());
    fixtureState.setFriction(fixture.getFriction());
    fixtureState.setDensity(fixture.getDensity());
    fixtureState.setSensor(fixture.isSensor());

    Filter filter = fixture.getFilterData();
    fixtureState.setFilterCategoryBits(filter.categoryBits);
    fixtureState.setFilterMaskBits(filter.maskBits);
    fixtureState.setFilterGroupIndex(filter.groupIndex);

    Shape shape = fixture.getShape();
    switch (shape.getType()) {
      case Circle: {
        CircleShape circle = (CircleShape) shape;
        CircleState circleState = new CircleState();
        circleState.setRadius(circle.getRadius());
        circleState.setCenter(circle.getPosition());
        fixtureState.setCircle(circleState);
      }
      break;
      case Edge: {
        EdgeShape edge = (EdgeShape) shape;
        EdgeState edgeState = new EdgeState();
        Vector2 v = new Vector2();
        edge.getVertex1(v);
        edgeState.setVertex1(v);
        edge.getVertex2(v);
        edgeState.setVertex2(v);
        fixtureState.setEdge(edgeState);
      }
      break;
      case Chain: {
        ChainShape chain = (ChainShape) shape;
        ChainState chainState = new ChainState();
        chainState.setVertices(new Array<Vector2>());
        int count = chain.getChildCount();
        for (int i = 0; i < count; ++i) {
          Vector2 v = new Vector2();
          chain.getVertex(i, v);
          chainState.getVertices().add(v);
        }
        fixtureState.setChain(chainState);
      }
      break;
      case Polygon: {
        PolygonShape poly = (PolygonShape) shape;
        PolygonState polygonState = new PolygonState();
        polygonState.setVertices(new Array<Vector2>());
        int vertexCount = poly.getVertexCount();
        for (int i = 0; i < vertexCount; ++i) {
          Vector2 v = new Vector2();
          poly.getVertex(i, v);
          polygonState.getVertices().add(v);
        }
        fixtureState.setPolygon(polygonState);
      }
      break;
      default:
        System.out.println("Unknown shape type : " + shape.getType());
    }

    return fixtureState;
  }

  public Box2dState fromWorld(World world) {
    Box2dState box2dState = new Box2dState();
    m_bodyToIndexMap.clear();
    m_jointToIndexMap.clear();

    box2dState.setGravity(world.getGravity());
    box2dState.setAutoClearForces(world.getAutoClearForces());

    Array<Body> bodyList = new Array<Body>();
    world.getBodies(bodyList);

    box2dState.setBody(new Array<BodyState>());

    int i = 0;
    for (Body body : bodyList) {
      m_bodyToIndexMap.put(body, i);
      BodyState bodyState = fromBody(body);
      box2dState.getBody().add(bodyState);
      i++;
    }

    box2dState.setJoint(new Array<JointState>());
    i = 0;
    Array<Joint> jointList = new Array<Joint>();
    world.getJoints(jointList);
    for (Joint joint : jointList) {
      if (joint.getType() == JointDef.JointType.GearJoint) {
        continue;
      }
      m_jointToIndexMap.put(joint, i);
      JointState jointState = fromJoint(joint);
      box2dState.getJoint().add(jointState);
      i++;
    }
    for (Joint joint : jointList) {
      if (joint.getType() != JointDef.JointType.GearJoint) {
        continue;
      }
      m_jointToIndexMap.put(joint, i);
      JointState jointState = fromJoint(joint);
      box2dState.getJoint().add(jointState);
      i++;
    }

    m_bodyToIndexMap.clear();
    m_jointToIndexMap.clear();

    return box2dState;
  }

  public World toWorld(Box2dState state) {
    World world = new World(state.getGravity(), false);

    int i;
    Array<BodyState> bodyStates = state.getBody();
    if (null != bodyStates) {
      int numBodyValues = bodyStates.size;
      for (i = 0; i < numBodyValues; i++) {
        BodyState bodyState = bodyStates.get(i);
        Body body = toBody(world, bodyState);
        m_bodies.add(body);
        m_indexToBodyMap.put(i, body);
      }
    }
    Array<JointState> jointStates = state.getJoint();
    if (null != jointStates) {
      int numJointValues = jointStates.size;
      for (i = 0; i < numJointValues; i++) {
        JointState jointState = jointStates.get(i);
        Joint joint = toJoint(world, jointState);
        m_joints.add(joint);
      }
    }

    return world;
  }

  public Body toBody(World world, BodyState bodyState) {
    BodyDef bodyDef = new BodyDef();
    switch (bodyState.getType()) {
      case 0:
        bodyDef.type = BodyDef.BodyType.StaticBody;
        break;
      case 1:
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        break;
      case 2:
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        break;
    }
    bodyDef.position.set(bodyState.getPosition());
    bodyDef.angle = bodyState.getAngle();
    bodyDef.linearVelocity.set(bodyState.getLinearVelocity());
    bodyDef.angularVelocity = bodyState.getAngularVelocity();
    bodyDef.linearDamping = bodyState.getLinearDamping();
    bodyDef.angularDamping = bodyState.getAngularDamping();
    bodyDef.gravityScale = bodyState.getGravityScale();

    bodyDef.allowSleep = bodyState.isAllowSleep();
    bodyDef.awake = bodyState.isAwake();
    bodyDef.fixedRotation = bodyState.isFixedRotation();
    bodyDef.bullet = bodyState.isBullet();
    bodyDef.active = bodyState.isActive();

    Body body = world.createBody(bodyDef);

    body.setUserData(bodyState.getUserData());

    int i;

    Array<FixtureState> fixtureStates = bodyState.getFixture();
    if (null != fixtureStates) {
      int numFixtureValues = fixtureStates.size;
      for (i = 0; i < numFixtureValues; i++) {
        FixtureState fixtureState = fixtureStates.get(i);
        Fixture fixture = toFixture(body, fixtureState);
      }
    }

    // may be necessary if user has overridden mass characteristics
    MassData massData = new MassData();
    massData.mass = bodyState.getMassDataMass();
    massData.center.set(bodyState.getMassDataCenter());
    massData.I = bodyState.getMassDataI();
    body.setMassData(massData);

    return body;
  }

  private Joint toJoint(World world, JointState jointState) {
    Joint joint = null;

    int bodyIndexA = jointState.getBodyA();
    int bodyIndexB = jointState.getBodyB();
    if (bodyIndexA >= m_bodies.size() || bodyIndexB >= m_bodies.size()) {
      return null;
    }

    // keep these in scope after the if/else below
    RevoluteJointDef revoluteDef;
    PrismaticJointDef prismaticDef;
    DistanceJointDef distanceDef;
    PulleyJointDef pulleyDef;
    MouseJointDef mouseDef;
    GearJointDef gearDef;
    WheelJointDef wheelDef;
    WeldJointDef weldDef;
    FrictionJointDef frictionDef;
    RopeJointDef ropeDef;

    // will be used to select one of the above to work with
    JointDef jointDef = null;

    Vector2 mouseJointTarget = new Vector2(0, 0);
    String type = jointState.getType();

    if (type.equals("revolute")) {
      jointDef = revoluteDef = new RevoluteJointDef();
      revoluteDef.localAnchorA.set(jointState.getAnchorA());
      revoluteDef.localAnchorB.set(jointState.getAnchorB());
      revoluteDef.referenceAngle = jointState.getRefAngle();
      revoluteDef.enableLimit = jointState.isEnableLimit();
      revoluteDef.lowerAngle = jointState.getLowerLimit();
      revoluteDef.upperAngle = jointState.getUpperLimit();
      revoluteDef.enableMotor = jointState.isEnableMotor();
      revoluteDef.motorSpeed = jointState.getMotorSpeed();
      revoluteDef.maxMotorTorque = jointState.getMaxMotorTorque();
    } else if (type.equals("prismatic")) {
      jointDef = prismaticDef = new PrismaticJointDef();
      prismaticDef.localAnchorA.set(jointState.getAnchorA());
      prismaticDef.localAnchorB.set(jointState.getAnchorB());
      prismaticDef.referenceAngle = jointState.getRefAngle();
      prismaticDef.enableLimit = jointState.isEnableLimit();
      prismaticDef.enableMotor = jointState.isEnableMotor();
      prismaticDef.motorSpeed = jointState.getMotorSpeed();
    } else if (type.equals("distance")) {
      jointDef = distanceDef = new DistanceJointDef();
      distanceDef.localAnchorA.set(jointState.getAnchorA());
      distanceDef.localAnchorB.set(jointState.getAnchorB());
      distanceDef.length = jointState.getLength();
      distanceDef.frequencyHz = jointState.getFrequency();
      distanceDef.dampingRatio = jointState.getDampingRatio();
    } else if (type.equals("pulley")) {
      jointDef = pulleyDef = new PulleyJointDef();
      pulleyDef.groundAnchorA.set(jointState.getGroundAnchorA());
      pulleyDef.groundAnchorB.set(jointState.getGroundAnchorB());
      pulleyDef.localAnchorA.set(jointState.getAnchorA());
      pulleyDef.localAnchorB.set(jointState.getAnchorB());
      pulleyDef.lengthA = jointState.getLengthA();
      pulleyDef.lengthB = jointState.getLengthB();
      pulleyDef.ratio = jointState.getRatio();
    } else if (type.equals("mouse")) {
      jointDef = mouseDef = new MouseJointDef();
      mouseJointTarget = jointState.getTarget();
      mouseDef.target.set(jointState.getAnchorB());
      mouseDef.maxForce = jointState.getMaxForce();
      mouseDef.frequencyHz = jointState.getFrequency();
      mouseDef.dampingRatio = jointState.getDampingRatio();
    } else if (type.equals("gear")) {
      jointDef = gearDef = new GearJointDef();
//      int jointIndex1 = jointValue.getInt("joint1");
//      int jointIndex2 = jointValue.getInt("joint2");
//      gearDef.joint1 = m_joints.get(jointIndex1);
//      gearDef.joint2 = m_joints.get(jointIndex2);
//      gearDef.ratio = jsonToFloat("ratio", jointValue);
    } // Wheel joints are apparently not implemented in JBox2D yet, but
    // when they are, commenting out the following section should work.
    else if (type.equals("wheel")) {
      jointDef = wheelDef = new WheelJointDef();
//      wheelDef.localAnchorA.set(jsonToVec("anchorA", jointValue));
//      wheelDef.localAnchorB.set(jsonToVec("anchorB", jointValue));
//      wheelDef.localAxisA.set(jsonToVec("localAxisA", jointValue));
//      wheelDef.enableMotor = jointValue.optBoolean("enableMotor", false);
//      wheelDef.motorSpeed = jsonToFloat("motorSpeed", jointValue);
//      wheelDef.maxMotorTorque = jsonToFloat("maxMotorTorque", jointValue);
//      wheelDef.frequencyHz = jsonToFloat("springFrequency", jointValue);
//      wheelDef.dampingRatio = jsonToFloat("springDampingRatio", jointValue);
    } // For now, we will make do with a revolute joint.
    else if (type.equals("wheel")) {
      jointDef = revoluteDef = new RevoluteJointDef();
//      revoluteDef.localAnchorA.set(jsonToVec("anchorA", jointValue));
//      revoluteDef.localAnchorB.set(jsonToVec("anchorB", jointValue));
//      revoluteDef.enableMotor = jointValue.optBoolean("enableMotor", false);
//      revoluteDef.motorSpeed = jsonToFloat("motorSpeed", jointValue);
//      revoluteDef.maxMotorTorque = jsonToFloat("maxMotorTorque", jointValue);
    } else if (type.equals("weld")) {
      jointDef = weldDef = new WeldJointDef();
      weldDef.localAnchorA.set(jointState.getAnchorA());
      weldDef.localAnchorB.set(jointState.getAnchorB());
      weldDef.referenceAngle = 0;
    } else if (type.equals("friction")) {
      jointDef = frictionDef = new FrictionJointDef();
      frictionDef.localAnchorA.set(jointState.getAnchorA());
      frictionDef.localAnchorB.set(jointState.getAnchorB());
      frictionDef.maxForce = jointState.getMaxForce();
      frictionDef.maxTorque = jointState.getMaxTorque();
    } else if (type.equals("rope")) {
      jointDef = ropeDef = new RopeJointDef();
      ropeDef.localAnchorA.set(jointState.getAnchorA());
      ropeDef.localAnchorB.set(jointState.getAnchorB());
      ropeDef.maxLength = jointState.getMaxLength();
    }

    if (null != jointDef) {
      jointDef.collideConnected = jointState.isCollideConnected();
      jointDef.bodyA = m_bodies.get(bodyIndexA);
      jointDef.bodyB = m_bodies.get(bodyIndexB);

      joint = world.createJoint(jointDef);

      joint.setUserData(jointState.getUserData());

      if (type.equals("mouse")) {
        ((MouseJoint) joint).setTarget(mouseJointTarget);
      }

      if (type.equals("distance")) {
        DistanceJoint dj = (DistanceJoint) joint;
        dj.setLength(jointState.getLength());
      }
    }

    return joint;
  }

  private Fixture toFixture(Body body, FixtureState fixtureState) {
    if (null == fixtureState) {
      return null;
    }

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.restitution = fixtureState.getResitution();
    fixtureDef.friction = fixtureState.getFriction();
    fixtureDef.density = fixtureState.getDensity();
    fixtureDef.isSensor = fixtureState.isSensor();

    fixtureDef.filter.categoryBits = fixtureState.getFilterCategoryBits();
    fixtureDef.filter.maskBits = fixtureState.getFilterMaskBits();
    fixtureDef.filter.groupIndex = fixtureState.getFilterGroupIndex();

    Fixture fixture = null;
    if (null != fixtureState.getCircle()) {
      CircleState circleState = fixtureState.getCircle();
      CircleShape circleShape = new CircleShape();
      circleShape.setRadius(circleState.getRadius());
      circleShape.setPosition(circleState.getCenter());
      fixtureDef.shape = circleShape;
      fixture = body.createFixture(fixtureDef);
    } else if (null != fixtureState.getEdge()) {
      EdgeState edgeState = fixtureState.getEdge();
      EdgeShape edgeShape = new EdgeShape();
      edgeShape.set(edgeState.getVertex1(), edgeState.getVertex2());
      fixtureDef.shape = edgeShape;
      fixture = body.createFixture(fixtureDef);
    } else if (null != fixtureState.getChain()) {
      ChainState chainState = fixtureState.getChain();
      ChainShape chainShape = new ChainShape();
      int numVertices = chainState.getVertices().size;
      Vector2 vertices[] = new Vector2[numVertices];
      for (int i = 0; i < numVertices; i++) {
        chainState.getVertices().get(i);
      }
      chainShape.createChain(vertices);
      fixtureDef.shape = chainShape;
      fixture = body.createFixture(fixtureDef);
    } else if (null != fixtureState.getPolygon()) {
      PolygonState polygonState = fixtureState.getPolygon();
      int numVertices = polygonState.getVertices().size;
      if (numVertices > 4) {
        System.out.println("Ignoring polygon fixture with too many vertices.");
      } else if (numVertices < 2) {
        System.out.println("Ignoring polygon fixture less than two vertices.");
      } else if (numVertices == 2) {
        System.out.println("Creating edge shape instead of polygon with two vertices.");
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(polygonState.getVertices().get(0), polygonState.getVertices().get(1));
        fixtureDef.shape = edgeShape;
        fixture = body.createFixture(fixtureDef);
      } else {
        PolygonShape polygonShape = new PolygonShape();
        Vector2[] vertices = new Vector2[numVertices];
        for (int i = 0; i < numVertices; i++) {
          Vector2 vector2 = polygonState.getVertices().get(i);
          vertices[i] = vector2;
        }
        polygonShape.set(vertices);
        fixtureDef.shape = polygonShape;
        fixture = body.createFixture(fixtureDef);
      }
    }

    return fixture;
  }

  public void clear() {
    m_indexToBodyMap.clear();
    m_bodyToIndexMap.clear();
    m_jointToIndexMap.clear();
    m_bodies.clear();
    m_joints.clear();
  }
}
