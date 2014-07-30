package pt.lsts.imc.agents;

import java.util.Arrays;

import com.google.common.eventbus.Subscribe;

import pt.lsts.imc.AgentContext;
import pt.lsts.imc.DesiredSpeed;
import pt.lsts.imc.DesiredZ;
import pt.lsts.imc.FollowRefState;
import pt.lsts.imc.FollowReference;
import pt.lsts.imc.ImcAgent;
import pt.lsts.imc.PlanControl;
import pt.lsts.imc.PlanManeuver;
import pt.lsts.imc.PlanSpecification;
import pt.lsts.imc.Reference;
import pt.lsts.imc.DesiredSpeed.SPEED_UNITS;
import pt.lsts.imc.PlanControl.OP;
import pt.lsts.imc.PlanControl.TYPE;
import pt.lsts.imc.annotations.Periodic;

public class ExecuteTrajectory implements ImcAgent {

	protected AgentContext ctx;
	protected boolean active = false;

	protected double[] lats = new double[] {41.185065,41.185776,41.184992,41.183991}; 
	protected double[] lons = new double[] {-8.705527,-8.706348,-8.707587,-8.706472};
	protected float[] depths = new float[] {0,0,0,0};

	protected int curDest = 0;

	@Override
	public void onStart(AgentContext conn) {
		this.ctx = conn;
		PlanControl startPlan = new PlanControl();
		startPlan.setType(TYPE.REQUEST);
		startPlan.setOp(OP.START);
		startPlan.setPlanId("follow_ref_test");
		FollowReference man = new FollowReference();
		man.setControlEnt((short)255);
		man.setControlSrc(65535);
		man.setAltitudeInterval(2);
		man.setTimeout(10);

		PlanSpecification spec = new PlanSpecification();
		spec.setPlanId("followref_test");
		spec.setStartManId("1");
		PlanManeuver pm = new PlanManeuver();
		pm.setData(man);
		pm.setManeuverId("1");
		spec.setManeuvers(Arrays.asList(pm));
		startPlan.setArg(spec);
		int reqId = 0;
		startPlan.setRequestId(reqId);
		startPlan.setFlags(0);
		ctx.send(startPlan);
	}

	@Subscribe
	public void on(FollowRefState state) {
		active = true;

		if ((state.getProximity() & FollowRefState.PROX_XY_NEAR) != 0) {
			System.out.println("Arrived! Going for next point");
			curDest = (curDest + 1) % 4;
		}
	}

	@Periodic(millisBetweenUpdates=1000)
	public void sendReference() {
		Reference ref = new Reference(
				(short)(Reference.FLAG_LOCATION | Reference.FLAG_SPEED | Reference.FLAG_Z),
				new DesiredSpeed(1, SPEED_UNITS.METERS_PS),
				new DesiredZ(depths[curDest], DesiredZ.Z_UNITS.DEPTH),
				Math.toRadians(lats[curDest]),
				Math.toRadians(lons[curDest]),0f
				);
		ctx.send(ref);
	}

	@Override
	public void onStop() {
		if (active) {
			PlanControl stop = new PlanControl();
			stop.setType(TYPE.REQUEST);
			stop.setOp(OP.STOP);
			ctx.send(stop);
		}
	}
}