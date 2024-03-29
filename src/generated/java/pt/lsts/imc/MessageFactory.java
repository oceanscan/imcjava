/*
 * Below is the copyright agreement for IMCJava.
 * 
 * Copyright (c) 2010-2016, Laboratório de Sistemas e Tecnologia Subaquática
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     - Neither the names of IMC, LSTS, IMCJava nor the names of its 
 *       contributors may be used to endorse or promote products derived from 
 *       this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL LABORATORIO DE SISTEMAS E TECNOLOGIA SUBAQUATICA
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT 
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package pt.lsts.imc;

public class MessageFactory {

	private static MessageFactory instance = null;

	private MessageFactory() {}

	public static MessageFactory getInstance() {

		 if (instance == null)
			instance = new MessageFactory();

		return instance;
	}

	public IMCMessage createTypedMessage(String msgName, IMCDefinition defs) {
		int msgId = defs.getMessageId(msgName);
		return createTypedMessage(msgId, defs);
	}
	private IMCMessage createTypedMessage(int mgid, IMCDefinition defs) {

		switch(mgid) {
			case EntityState.ID_STATIC:
				return new EntityState(defs);
			case QueryEntityState.ID_STATIC:
				return new QueryEntityState(defs);
			case EntityInfo.ID_STATIC:
				return new EntityInfo(defs);
			case QueryEntityInfo.ID_STATIC:
				return new QueryEntityInfo(defs);
			case EntityList.ID_STATIC:
				return new EntityList(defs);
			case CpuUsage.ID_STATIC:
				return new CpuUsage(defs);
			case TransportBindings.ID_STATIC:
				return new TransportBindings(defs);
			case RestartSystem.ID_STATIC:
				return new RestartSystem(defs);
			case MemUsage.ID_STATIC:
				return new MemUsage(defs);
			case EntityActivationState.ID_STATIC:
				return new EntityActivationState(defs);
			case QueryEntityActivationState.ID_STATIC:
				return new QueryEntityActivationState(defs);
			case VehicleOperationalLimits.ID_STATIC:
				return new VehicleOperationalLimits(defs);
			case MsgList.ID_STATIC:
				return new MsgList(defs);
			case SimulatedState.ID_STATIC:
				return new SimulatedState(defs);
			case StorageUsage.ID_STATIC:
				return new StorageUsage(defs);
			case CacheControl.ID_STATIC:
				return new CacheControl(defs);
			case LoggingControl.ID_STATIC:
				return new LoggingControl(defs);
			case LogBookEntry.ID_STATIC:
				return new LogBookEntry(defs);
			case LogBookControl.ID_STATIC:
				return new LogBookControl(defs);
			case ReplayControl.ID_STATIC:
				return new ReplayControl(defs);
			case ClockControl.ID_STATIC:
				return new ClockControl(defs);
			case Heartbeat.ID_STATIC:
				return new Heartbeat(defs);
			case Announce.ID_STATIC:
				return new Announce(defs);
			case AnnounceService.ID_STATIC:
				return new AnnounceService(defs);
			case RSSI.ID_STATIC:
				return new RSSI(defs);
			case Sms.ID_STATIC:
				return new Sms(defs);
			case SmsTx.ID_STATIC:
				return new SmsTx(defs);
			case SmsRx.ID_STATIC:
				return new SmsRx(defs);
			case SmsState.ID_STATIC:
				return new SmsState(defs);
			case TextMessage.ID_STATIC:
				return new TextMessage(defs);
			case IridiumMsgRx.ID_STATIC:
				return new IridiumMsgRx(defs);
			case IridiumMsgTx.ID_STATIC:
				return new IridiumMsgTx(defs);
			case IridiumTxStatus.ID_STATIC:
				return new IridiumTxStatus(defs);
			case ExtendedRSSI.ID_STATIC:
				return new ExtendedRSSI(defs);
			case LblRange.ID_STATIC:
				return new LblRange(defs);
			case LblBeacon.ID_STATIC:
				return new LblBeacon(defs);
			case LblConfig.ID_STATIC:
				return new LblConfig(defs);
			case AcousticOperation.ID_STATIC:
				return new AcousticOperation(defs);
			case AcousticSystemsQuery.ID_STATIC:
				return new AcousticSystemsQuery(defs);
			case AcousticSystems.ID_STATIC:
				return new AcousticSystems(defs);
			case AcousticLink.ID_STATIC:
				return new AcousticLink(defs);
			case Rpm.ID_STATIC:
				return new Rpm(defs);
			case Voltage.ID_STATIC:
				return new Voltage(defs);
			case Current.ID_STATIC:
				return new Current(defs);
			case GpsFix.ID_STATIC:
				return new GpsFix(defs);
			case EulerAngles.ID_STATIC:
				return new EulerAngles(defs);
			case EulerAnglesDelta.ID_STATIC:
				return new EulerAnglesDelta(defs);
			case AngularVelocity.ID_STATIC:
				return new AngularVelocity(defs);
			case Acceleration.ID_STATIC:
				return new Acceleration(defs);
			case MagneticField.ID_STATIC:
				return new MagneticField(defs);
			case GroundVelocity.ID_STATIC:
				return new GroundVelocity(defs);
			case WaterVelocity.ID_STATIC:
				return new WaterVelocity(defs);
			case VelocityDelta.ID_STATIC:
				return new VelocityDelta(defs);
			case Distance.ID_STATIC:
				return new Distance(defs);
			case Temperature.ID_STATIC:
				return new Temperature(defs);
			case Pressure.ID_STATIC:
				return new Pressure(defs);
			case Depth.ID_STATIC:
				return new Depth(defs);
			case SoundSpeed.ID_STATIC:
				return new SoundSpeed(defs);
			case WaterDensity.ID_STATIC:
				return new WaterDensity(defs);
			case Conductivity.ID_STATIC:
				return new Conductivity(defs);
			case Salinity.ID_STATIC:
				return new Salinity(defs);
			case RelativeHumidity.ID_STATIC:
				return new RelativeHumidity(defs);
			case DevDataText.ID_STATIC:
				return new DevDataText(defs);
			case DevDataBinary.ID_STATIC:
				return new DevDataBinary(defs);
			case SonarData.ID_STATIC:
				return new SonarData(defs);
			case Pulse.ID_STATIC:
				return new Pulse(defs);
			case PulseDetectionControl.ID_STATIC:
				return new PulseDetectionControl(defs);
			case FuelLevel.ID_STATIC:
				return new FuelLevel(defs);
			case ServoPosition.ID_STATIC:
				return new ServoPosition(defs);
			case DeviceState.ID_STATIC:
				return new DeviceState(defs);
			case BeamConfig.ID_STATIC:
				return new BeamConfig(defs);
			case DataSanity.ID_STATIC:
				return new DataSanity(defs);
			case RhodamineDye.ID_STATIC:
				return new RhodamineDye(defs);
			case CrudeOil.ID_STATIC:
				return new CrudeOil(defs);
			case FineOil.ID_STATIC:
				return new FineOil(defs);
			case Turbidity.ID_STATIC:
				return new Turbidity(defs);
			case Chlorophyll.ID_STATIC:
				return new Chlorophyll(defs);
			case Fluorescein.ID_STATIC:
				return new Fluorescein(defs);
			case Phycocyanin.ID_STATIC:
				return new Phycocyanin(defs);
			case Phycoerythrin.ID_STATIC:
				return new Phycoerythrin(defs);
			case DissolvedOxygen.ID_STATIC:
				return new DissolvedOxygen(defs);
			case AirSaturation.ID_STATIC:
				return new AirSaturation(defs);
			case PH.ID_STATIC:
				return new PH(defs);
			case Redox.ID_STATIC:
				return new Redox(defs);
			case SetThrusterActuation.ID_STATIC:
				return new SetThrusterActuation(defs);
			case SetServoPosition.ID_STATIC:
				return new SetServoPosition(defs);
			case RemoteActionsRequest.ID_STATIC:
				return new RemoteActionsRequest(defs);
			case RemoteActions.ID_STATIC:
				return new RemoteActions(defs);
			case ButtonEvent.ID_STATIC:
				return new ButtonEvent(defs);
			case LcdControl.ID_STATIC:
				return new LcdControl(defs);
			case PowerOperation.ID_STATIC:
				return new PowerOperation(defs);
			case PowerChannelControl.ID_STATIC:
				return new PowerChannelControl(defs);
			case QueryPowerChannelState.ID_STATIC:
				return new QueryPowerChannelState(defs);
			case PowerChannelState.ID_STATIC:
				return new PowerChannelState(defs);
			case LedBrightness.ID_STATIC:
				return new LedBrightness(defs);
			case QueryLedBrightness.ID_STATIC:
				return new QueryLedBrightness(defs);
			case SetLedBrightness.ID_STATIC:
				return new SetLedBrightness(defs);
			case SetPWM.ID_STATIC:
				return new SetPWM(defs);
			case EstimatedState.ID_STATIC:
				return new EstimatedState(defs);
			case EstimatedStreamVelocity.ID_STATIC:
				return new EstimatedStreamVelocity(defs);
			case NavigationUncertainty.ID_STATIC:
				return new NavigationUncertainty(defs);
			case NavigationData.ID_STATIC:
				return new NavigationData(defs);
			case GpsFixRejection.ID_STATIC:
				return new GpsFixRejection(defs);
			case LblRangeAcceptance.ID_STATIC:
				return new LblRangeAcceptance(defs);
			case DvlRejection.ID_STATIC:
				return new DvlRejection(defs);
			case LblEstimate.ID_STATIC:
				return new LblEstimate(defs);
			case AlignmentState.ID_STATIC:
				return new AlignmentState(defs);
			case DesiredHeading.ID_STATIC:
				return new DesiredHeading(defs);
			case DesiredZ.ID_STATIC:
				return new DesiredZ(defs);
			case DesiredSpeed.ID_STATIC:
				return new DesiredSpeed(defs);
			case DesiredRoll.ID_STATIC:
				return new DesiredRoll(defs);
			case DesiredPitch.ID_STATIC:
				return new DesiredPitch(defs);
			case DesiredPath.ID_STATIC:
				return new DesiredPath(defs);
			case DesiredControl.ID_STATIC:
				return new DesiredControl(defs);
			case DesiredHeadingRate.ID_STATIC:
				return new DesiredHeadingRate(defs);
			case PathControlState.ID_STATIC:
				return new PathControlState(defs);
			case AllocatedControlTorques.ID_STATIC:
				return new AllocatedControlTorques(defs);
			case ControlParcel.ID_STATIC:
				return new ControlParcel(defs);
			case Brake.ID_STATIC:
				return new Brake(defs);
			case Goto.ID_STATIC:
				return new Goto(defs);
			case PopUp.ID_STATIC:
				return new PopUp(defs);
			case Teleoperation.ID_STATIC:
				return new Teleoperation(defs);
			case Loiter.ID_STATIC:
				return new Loiter(defs);
			case IdleManeuver.ID_STATIC:
				return new IdleManeuver(defs);
			case Rows.ID_STATIC:
				return new Rows(defs);
			case FollowPath.ID_STATIC:
				return new FollowPath(defs);
			case PathPoint.ID_STATIC:
				return new PathPoint(defs);
			case YoYo.ID_STATIC:
				return new YoYo(defs);
			case TeleoperationDone.ID_STATIC:
				return new TeleoperationDone(defs);
			case StationKeeping.ID_STATIC:
				return new StationKeeping(defs);
			case Elevator.ID_STATIC:
				return new Elevator(defs);
			case FollowTrajectory.ID_STATIC:
				return new FollowTrajectory(defs);
			case TrajectoryPoint.ID_STATIC:
				return new TrajectoryPoint(defs);
			case CustomManeuver.ID_STATIC:
				return new CustomManeuver(defs);
			case StopManeuver.ID_STATIC:
				return new StopManeuver(defs);
			case RegisterManeuver.ID_STATIC:
				return new RegisterManeuver(defs);
			case ManeuverControlState.ID_STATIC:
				return new ManeuverControlState(defs);
			case PolygonVertex.ID_STATIC:
				return new PolygonVertex(defs);
			case CompassCalibration.ID_STATIC:
				return new CompassCalibration(defs);
			case FollowReference.ID_STATIC:
				return new FollowReference(defs);
			case Reference.ID_STATIC:
				return new Reference(defs);
			case FollowRefState.ID_STATIC:
				return new FollowRefState(defs);
			case Dislodge.ID_STATIC:
				return new Dislodge(defs);
			case Launch.ID_STATIC:
				return new Launch(defs);
			case Alignment.ID_STATIC:
				return new Alignment(defs);
			case FollowCommand.ID_STATIC:
				return new FollowCommand(defs);
			case Command.ID_STATIC:
				return new Command(defs);
			case FollowCommandState.ID_STATIC:
				return new FollowCommandState(defs);
			case Magnetometer.ID_STATIC:
				return new Magnetometer(defs);
			case VehicleState.ID_STATIC:
				return new VehicleState(defs);
			case VehicleCommand.ID_STATIC:
				return new VehicleCommand(defs);
			case MonitorEntityState.ID_STATIC:
				return new MonitorEntityState(defs);
			case EntityMonitoringState.ID_STATIC:
				return new EntityMonitoringState(defs);
			case OperationalLimits.ID_STATIC:
				return new OperationalLimits(defs);
			case GetOperationalLimits.ID_STATIC:
				return new GetOperationalLimits(defs);
			case Calibration.ID_STATIC:
				return new Calibration(defs);
			case ControlLoops.ID_STATIC:
				return new ControlLoops(defs);
			case VehicleMedium.ID_STATIC:
				return new VehicleMedium(defs);
			case Collision.ID_STATIC:
				return new Collision(defs);
			case ReportControl.ID_STATIC:
				return new ReportControl(defs);
			case AssetReport.ID_STATIC:
				return new AssetReport(defs);
			case SmsRequest.ID_STATIC:
				return new SmsRequest(defs);
			case SmsStatus.ID_STATIC:
				return new SmsStatus(defs);
			case Abort.ID_STATIC:
				return new Abort(defs);
			case PlanSpecification.ID_STATIC:
				return new PlanSpecification(defs);
			case PlanManeuver.ID_STATIC:
				return new PlanManeuver(defs);
			case PlanTransition.ID_STATIC:
				return new PlanTransition(defs);
			case PlanDB.ID_STATIC:
				return new PlanDB(defs);
			case PlanDBState.ID_STATIC:
				return new PlanDBState(defs);
			case PlanDBInformation.ID_STATIC:
				return new PlanDBInformation(defs);
			case PlanControl.ID_STATIC:
				return new PlanControl(defs);
			case PlanControlState.ID_STATIC:
				return new PlanControlState(defs);
			case PlanVariable.ID_STATIC:
				return new PlanVariable(defs);
			case PlanGeneration.ID_STATIC:
				return new PlanGeneration(defs);
			case PlanStatistics.ID_STATIC:
				return new PlanStatistics(defs);
			case CcuEvent.ID_STATIC:
				return new CcuEvent(defs);
			case VehicleLinks.ID_STATIC:
				return new VehicleLinks(defs);
			case TrexOperation.ID_STATIC:
				return new TrexOperation(defs);
			case TrexAttribute.ID_STATIC:
				return new TrexAttribute(defs);
			case TrexToken.ID_STATIC:
				return new TrexToken(defs);
			case EntityParameter.ID_STATIC:
				return new EntityParameter(defs);
			case EntityParameters.ID_STATIC:
				return new EntityParameters(defs);
			case QueryEntityParameters.ID_STATIC:
				return new QueryEntityParameters(defs);
			case SetEntityParameters.ID_STATIC:
				return new SetEntityParameters(defs);
			case SaveEntityParameters.ID_STATIC:
				return new SaveEntityParameters(defs);
			case PushEntityParameters.ID_STATIC:
				return new PushEntityParameters(defs);
			case PopEntityParameters.ID_STATIC:
				return new PopEntityParameters(defs);
			case IoEvent.ID_STATIC:
				return new IoEvent(defs);
			case UamTxFrame.ID_STATIC:
				return new UamTxFrame(defs);
			case UamRxFrame.ID_STATIC:
				return new UamRxFrame(defs);
			case UamTxStatus.ID_STATIC:
				return new UamTxStatus(defs);
			case UamRxRange.ID_STATIC:
				return new UamRxRange(defs);
			case UamTxRange.ID_STATIC:
				return new UamTxRange(defs);
			case MessagePart.ID_STATIC:
				return new MessagePart(defs);
			case Aborted.ID_STATIC:
				return new Aborted(defs);
			case UsblAngles.ID_STATIC:
				return new UsblAngles(defs);
			case UsblPosition.ID_STATIC:
				return new UsblPosition(defs);
			case UsblFix.ID_STATIC:
				return new UsblFix(defs);
			case UsblAnglesExtended.ID_STATIC:
				return new UsblAnglesExtended(defs);
			case UsblPositionExtended.ID_STATIC:
				return new UsblPositionExtended(defs);
			case UsblFixExtended.ID_STATIC:
				return new UsblFixExtended(defs);
			case UsblModem.ID_STATIC:
				return new UsblModem(defs);
			case UsblConfig.ID_STATIC:
				return new UsblConfig(defs);
			case DissolvedOrganicMatter.ID_STATIC:
				return new DissolvedOrganicMatter(defs);
			case OpticalBackscatter.ID_STATIC:
				return new OpticalBackscatter(defs);
			case Tachograph.ID_STATIC:
				return new Tachograph(defs);
			case CurrentProfile.ID_STATIC:
				return new CurrentProfile(defs);
			case CurrentProfileCell.ID_STATIC:
				return new CurrentProfileCell(defs);
			case ADCPBeam.ID_STATIC:
				return new ADCPBeam(defs);
			case GpioState.ID_STATIC:
				return new GpioState(defs);
			case GpioStateGet.ID_STATIC:
				return new GpioStateGet(defs);
			case GpioStateSet.ID_STATIC:
				return new GpioStateSet(defs);
			case ColoredDissolvedOrganicMatter.ID_STATIC:
				return new ColoredDissolvedOrganicMatter(defs);
			case FluorescentDissolvedOrganicMatter.ID_STATIC:
				return new FluorescentDissolvedOrganicMatter(defs);
			case IridiumMsgTxExtended.ID_STATIC:
				return new IridiumMsgTxExtended(defs);
			case TotalMagIntensity.ID_STATIC:
				return new TotalMagIntensity(defs);
			case ValidatePlan.ID_STATIC:
				return new ValidatePlan(defs);
			case CommRestriction.ID_STATIC:
				return new CommRestriction(defs);
			case WifiStats.ID_STATIC:
				return new WifiStats(defs);
			case WifiNetwork.ID_STATIC:
				return new WifiNetwork(defs);
			case SonarPulse.ID_STATIC:
				return new SonarPulse(defs);
			case ChargingState.ID_STATIC:
				return new ChargingState(defs);
			case HealthCheck.ID_STATIC:
				return new HealthCheck(defs);
			case QueryTypedEntityParameters.ID_STATIC:
				return new QueryTypedEntityParameters(defs);
			case TypedEntityParameter.ID_STATIC:
				return new TypedEntityParameter(defs);
			case ValuesIf.ID_STATIC:
				return new ValuesIf(defs);
			case DirSonarData.ID_STATIC:
				return new DirSonarData(defs);
			default:
				return new IMCMessage(defs);
		}
	}
}
