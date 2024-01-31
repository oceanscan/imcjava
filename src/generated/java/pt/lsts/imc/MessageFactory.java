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

		if (65109 == defs.getSyncWord()) {
			switch(mgid) {
				case 1:
					return new EntityState(defs, 1);
				case 2:
					return new QueryEntityState(defs, 2);
				case 3:
					return new EntityInfo(defs, 3);
				case 4:
					return new QueryEntityInfo(defs, 4);
				case 5:
					return new EntityList(defs, 5);
				case 7:
					return new CpuUsage(defs, 7);
				case 8:
					return new TransportBindings(defs, 8);
				case 9:
					return new RestartSystem(defs, 9);
				case 12:
					return new DevCalibrationControl(defs, 12);
				case 13:
					return new DevCalibrationState(defs, 13);
				case 14:
					return new EntityActivationState(defs, 14);
				case 15:
					return new QueryEntityActivationState(defs, 15);
				case 16:
					return new VehicleOperationalLimits(defs, 16);
				case 20:
					return new MsgList(defs, 20);
				case 50:
					return new SimulatedState(defs, 50);
				case 51:
					return new LeakSimulation(defs, 51);
				case 52:
					return new UASimulation(defs, 52);
				case 53:
					return new DynamicsSimParam(defs, 53);
				case 100:
					return new StorageUsage(defs, 100);
				case 101:
					return new CacheControl(defs, 101);
				case 102:
					return new LoggingControl(defs, 102);
				case 103:
					return new LogBookEntry(defs, 103);
				case 104:
					return new LogBookControl(defs, 104);
				case 105:
					return new ReplayControl(defs, 105);
				case 106:
					return new ClockControl(defs, 106);
				case 107:
					return new HistoricCTD(defs, 107);
				case 108:
					return new HistoricTelemetry(defs, 108);
				case 109:
					return new HistoricSonarData(defs, 109);
				case 110:
					return new HistoricEvent(defs, 110);
				case 111:
					return new VerticalProfile(defs, 111);
				case 112:
					return new ProfileSample(defs, 112);
				case 150:
					return new Heartbeat(defs, 150);
				case 151:
					return new Announce(defs, 151);
				case 152:
					return new AnnounceService(defs, 152);
				case 153:
					return new RSSI(defs, 153);
				case 154:
					return new VSWR(defs, 154);
				case 155:
					return new LinkLevel(defs, 155);
				case 156:
					return new Sms(defs, 156);
				case 157:
					return new SmsTx(defs, 157);
				case 158:
					return new SmsRx(defs, 158);
				case 159:
					return new SmsState(defs, 159);
				case 160:
					return new TextMessage(defs, 160);
				case 170:
					return new IridiumMsgRx(defs, 170);
				case 171:
					return new IridiumMsgTx(defs, 171);
				case 172:
					return new IridiumTxStatus(defs, 172);
				case 180:
					return new GroupMembershipState(defs, 180);
				case 181:
					return new SystemGroup(defs, 181);
				case 182:
					return new LinkLatency(defs, 182);
				case 183:
					return new ExtendedRSSI(defs, 183);
				case 184:
					return new HistoricData(defs, 184);
				case 185:
					return new CompressedHistory(defs, 185);
				case 186:
					return new HistoricSample(defs, 186);
				case 187:
					return new HistoricDataQuery(defs, 187);
				case 188:
					return new RemoteCommand(defs, 188);
				case 189:
					return new CommSystemsQuery(defs, 189);
				case 190:
					return new TelemetryMsg(defs, 190);
				case 200:
					return new LblRange(defs, 200);
				case 202:
					return new LblBeacon(defs, 202);
				case 203:
					return new LblConfig(defs, 203);
				case 204:
					return new LblBeaconExtended(defs, 204);
				case 205:
					return new LblConfigExtended(defs, 205);
				case 206:
					return new AcousticMessage(defs, 206);
				case 207:
					return new SimAcousticMessage(defs, 207);
				case 211:
					return new AcousticOperation(defs, 211);
				case 212:
					return new AcousticSystemsQuery(defs, 212);
				case 213:
					return new AcousticSystems(defs, 213);
				case 214:
					return new AcousticLink(defs, 214);
				case 215:
					return new AcousticRequest(defs, 215);
				case 216:
					return new AcousticStatus(defs, 216);
				case 250:
					return new Rpm(defs, 250);
				case 251:
					return new Voltage(defs, 251);
				case 252:
					return new Current(defs, 252);
				case 253:
					return new GpsFix(defs, 253);
				case 254:
					return new EulerAngles(defs, 254);
				case 255:
					return new EulerAnglesDelta(defs, 255);
				case 256:
					return new AngularVelocity(defs, 256);
				case 257:
					return new Acceleration(defs, 257);
				case 258:
					return new MagneticField(defs, 258);
				case 259:
					return new GroundVelocity(defs, 259);
				case 260:
					return new WaterVelocity(defs, 260);
				case 261:
					return new VelocityDelta(defs, 261);
				case 262:
					return new Distance(defs, 262);
				case 263:
					return new Temperature(defs, 263);
				case 264:
					return new Pressure(defs, 264);
				case 265:
					return new Depth(defs, 265);
				case 266:
					return new DepthOffset(defs, 266);
				case 267:
					return new SoundSpeed(defs, 267);
				case 268:
					return new WaterDensity(defs, 268);
				case 269:
					return new Conductivity(defs, 269);
				case 270:
					return new Salinity(defs, 270);
				case 271:
					return new WindSpeed(defs, 271);
				case 272:
					return new RelativeHumidity(defs, 272);
				case 273:
					return new DevDataText(defs, 273);
				case 274:
					return new DevDataBinary(defs, 274);
				case 275:
					return new Force(defs, 275);
				case 276:
					return new SonarData(defs, 276);
				case 277:
					return new Pulse(defs, 277);
				case 278:
					return new PulseDetectionControl(defs, 278);
				case 279:
					return new FuelLevel(defs, 279);
				case 280:
					return new GpsNavData(defs, 280);
				case 281:
					return new ServoPosition(defs, 281);
				case 282:
					return new DeviceState(defs, 282);
				case 283:
					return new BeamConfig(defs, 283);
				case 284:
					return new DataSanity(defs, 284);
				case 285:
					return new RhodamineDye(defs, 285);
				case 286:
					return new CrudeOil(defs, 286);
				case 287:
					return new FineOil(defs, 287);
				case 288:
					return new Turbidity(defs, 288);
				case 289:
					return new Chlorophyll(defs, 289);
				case 290:
					return new Fluorescein(defs, 290);
				case 291:
					return new Phycocyanin(defs, 291);
				case 292:
					return new Phycoerythrin(defs, 292);
				case 293:
					return new GpsFixRtk(defs, 293);
				case 294:
					return new ExternalNavData(defs, 294);
				case 295:
					return new DissolvedOxygen(defs, 295);
				case 296:
					return new AirSaturation(defs, 296);
				case 297:
					return new Throttle(defs, 297);
				case 298:
					return new PH(defs, 298);
				case 299:
					return new Redox(defs, 299);
				case 300:
					return new CameraZoom(defs, 300);
				case 301:
					return new SetThrusterActuation(defs, 301);
				case 302:
					return new SetServoPosition(defs, 302);
				case 303:
					return new SetControlSurfaceDeflection(defs, 303);
				case 304:
					return new RemoteActionsRequest(defs, 304);
				case 305:
					return new RemoteActions(defs, 305);
				case 306:
					return new ButtonEvent(defs, 306);
				case 307:
					return new LcdControl(defs, 307);
				case 308:
					return new PowerOperation(defs, 308);
				case 309:
					return new PowerChannelControl(defs, 309);
				case 310:
					return new QueryPowerChannelState(defs, 310);
				case 311:
					return new PowerChannelState(defs, 311);
				case 312:
					return new LedBrightness(defs, 312);
				case 313:
					return new QueryLedBrightness(defs, 313);
				case 314:
					return new SetLedBrightness(defs, 314);
				case 315:
					return new SetPWM(defs, 315);
				case 316:
					return new PWM(defs, 316);
				case 350:
					return new EstimatedState(defs, 350);
				case 351:
					return new EstimatedStreamVelocity(defs, 351);
				case 352:
					return new IndicatedSpeed(defs, 352);
				case 353:
					return new TrueSpeed(defs, 353);
				case 354:
					return new NavigationUncertainty(defs, 354);
				case 355:
					return new NavigationData(defs, 355);
				case 356:
					return new GpsFixRejection(defs, 356);
				case 357:
					return new LblRangeAcceptance(defs, 357);
				case 358:
					return new DvlRejection(defs, 358);
				case 360:
					return new LblEstimate(defs, 360);
				case 361:
					return new AlignmentState(defs, 361);
				case 362:
					return new GroupStreamVelocity(defs, 362);
				case 363:
					return new Airflow(defs, 363);
				case 400:
					return new DesiredHeading(defs, 400);
				case 401:
					return new DesiredZ(defs, 401);
				case 402:
					return new DesiredSpeed(defs, 402);
				case 403:
					return new DesiredRoll(defs, 403);
				case 404:
					return new DesiredPitch(defs, 404);
				case 405:
					return new DesiredVerticalRate(defs, 405);
				case 406:
					return new DesiredPath(defs, 406);
				case 407:
					return new DesiredControl(defs, 407);
				case 408:
					return new DesiredHeadingRate(defs, 408);
				case 409:
					return new DesiredVelocity(defs, 409);
				case 410:
					return new PathControlState(defs, 410);
				case 411:
					return new AllocatedControlTorques(defs, 411);
				case 412:
					return new ControlParcel(defs, 412);
				case 413:
					return new Brake(defs, 413);
				case 414:
					return new DesiredLinearState(defs, 414);
				case 415:
					return new DesiredThrottle(defs, 415);
				case 450:
					return new Goto(defs, 450);
				case 451:
					return new PopUp(defs, 451);
				case 452:
					return new Teleoperation(defs, 452);
				case 453:
					return new Loiter(defs, 453);
				case 454:
					return new IdleManeuver(defs, 454);
				case 455:
					return new LowLevelControl(defs, 455);
				case 456:
					return new Rows(defs, 456);
				case 457:
					return new FollowPath(defs, 457);
				case 458:
					return new PathPoint(defs, 458);
				case 459:
					return new YoYo(defs, 459);
				case 460:
					return new TeleoperationDone(defs, 460);
				case 461:
					return new StationKeeping(defs, 461);
				case 462:
					return new Elevator(defs, 462);
				case 463:
					return new FollowTrajectory(defs, 463);
				case 464:
					return new TrajectoryPoint(defs, 464);
				case 465:
					return new CustomManeuver(defs, 465);
				case 466:
					return new VehicleFormation(defs, 466);
				case 467:
					return new VehicleFormationParticipant(defs, 467);
				case 468:
					return new StopManeuver(defs, 468);
				case 469:
					return new RegisterManeuver(defs, 469);
				case 470:
					return new ManeuverControlState(defs, 470);
				case 471:
					return new FollowSystem(defs, 471);
				case 472:
					return new CommsRelay(defs, 472);
				case 473:
					return new CoverArea(defs, 473);
				case 474:
					return new PolygonVertex(defs, 474);
				case 475:
					return new CompassCalibration(defs, 475);
				case 476:
					return new FormationParameters(defs, 476);
				case 477:
					return new FormationPlanExecution(defs, 477);
				case 478:
					return new FollowReference(defs, 478);
				case 479:
					return new Reference(defs, 479);
				case 480:
					return new FollowRefState(defs, 480);
				case 481:
					return new FormationMonitor(defs, 481);
				case 482:
					return new RelativeState(defs, 482);
				case 483:
					return new Dislodge(defs, 483);
				case 484:
					return new Formation(defs, 484);
				case 485:
					return new Launch(defs, 485);
				case 486:
					return new Drop(defs, 486);
				case 487:
					return new ScheduledGoto(defs, 487);
				case 488:
					return new RowsCoverage(defs, 488);
				case 489:
					return new Sample(defs, 489);
				case 490:
					return new ImageTracking(defs, 490);
				case 491:
					return new Takeoff(defs, 491);
				case 492:
					return new Land(defs, 492);
				case 493:
					return new AutonomousSection(defs, 493);
				case 494:
					return new FollowPoint(defs, 494);
				case 495:
					return new Alignment(defs, 495);
				case 496:
					return new FollowCommand(defs, 496);
				case 497:
					return new Command(defs, 497);
				case 498:
					return new FollowCommandState(defs, 498);
				case 499:
					return new Magnetometer(defs, 499);
				case 500:
					return new VehicleState(defs, 500);
				case 501:
					return new VehicleCommand(defs, 501);
				case 502:
					return new MonitorEntityState(defs, 502);
				case 503:
					return new EntityMonitoringState(defs, 503);
				case 504:
					return new OperationalLimits(defs, 504);
				case 505:
					return new GetOperationalLimits(defs, 505);
				case 506:
					return new Calibration(defs, 506);
				case 507:
					return new ControlLoops(defs, 507);
				case 508:
					return new VehicleMedium(defs, 508);
				case 509:
					return new Collision(defs, 509);
				case 510:
					return new FormState(defs, 510);
				case 511:
					return new AutopilotMode(defs, 511);
				case 512:
					return new FormationState(defs, 512);
				case 513:
					return new ReportControl(defs, 513);
				case 514:
					return new StateReport(defs, 514);
				case 515:
					return new TransmissionRequest(defs, 515);
				case 516:
					return new TransmissionStatus(defs, 516);
				case 517:
					return new SmsRequest(defs, 517);
				case 518:
					return new SmsStatus(defs, 518);
				case 519:
					return new VtolState(defs, 519);
				case 520:
					return new ArmingState(defs, 520);
				case 521:
					return new TCPRequest(defs, 521);
				case 522:
					return new TCPStatus(defs, 522);
				case 525:
					return new AssetReport(defs, 525);
				case 550:
					return new Abort(defs, 550);
				case 551:
					return new PlanSpecification(defs, 551);
				case 552:
					return new PlanManeuver(defs, 552);
				case 553:
					return new PlanTransition(defs, 553);
				case 554:
					return new EmergencyControl(defs, 554);
				case 555:
					return new EmergencyControlState(defs, 555);
				case 556:
					return new PlanDB(defs, 556);
				case 557:
					return new PlanDBState(defs, 557);
				case 558:
					return new PlanDBInformation(defs, 558);
				case 559:
					return new PlanControl(defs, 559);
				case 560:
					return new PlanControlState(defs, 560);
				case 561:
					return new PlanVariable(defs, 561);
				case 562:
					return new PlanGeneration(defs, 562);
				case 563:
					return new LeaderState(defs, 563);
				case 564:
					return new PlanStatistics(defs, 564);
				case 600:
					return new ReportedState(defs, 600);
				case 601:
					return new RemoteSensorInfo(defs, 601);
				case 602:
					return new Map(defs, 602);
				case 603:
					return new MapFeature(defs, 603);
				case 604:
					return new MapPoint(defs, 604);
				case 606:
					return new CcuEvent(defs, 606);
				case 650:
					return new VehicleLinks(defs, 650);
				case 651:
					return new TrexObservation(defs, 651);
				case 652:
					return new TrexCommand(defs, 652);
				case 655:
					return new TrexOperation(defs, 655);
				case 656:
					return new TrexAttribute(defs, 656);
				case 657:
					return new TrexToken(defs, 657);
				case 658:
					return new TrexPlan(defs, 658);
				case 660:
					return new Event(defs, 660);
				case 702:
					return new CompressedImage(defs, 702);
				case 703:
					return new ImageTxSettings(defs, 703);
				case 719:
					return new ManeuverDone(defs, 719);
				case 720:
					return new StationKeepingExtended(defs, 720);
				case 750:
					return new RemoteState(defs, 750);
				case 800:
					return new Target(defs, 800);
				case 801:
					return new EntityParameter(defs, 801);
				case 802:
					return new EntityParameters(defs, 802);
				case 803:
					return new QueryEntityParameters(defs, 803);
				case 804:
					return new SetEntityParameters(defs, 804);
				case 805:
					return new SaveEntityParameters(defs, 805);
				case 806:
					return new CreateSession(defs, 806);
				case 807:
					return new CloseSession(defs, 807);
				case 808:
					return new SessionSubscription(defs, 808);
				case 809:
					return new SessionKeepAlive(defs, 809);
				case 810:
					return new SessionStatus(defs, 810);
				case 811:
					return new PushEntityParameters(defs, 811);
				case 812:
					return new PopEntityParameters(defs, 812);
				case 813:
					return new IoEvent(defs, 813);
				case 814:
					return new UamTxFrame(defs, 814);
				case 815:
					return new UamRxFrame(defs, 815);
				case 816:
					return new UamTxStatus(defs, 816);
				case 817:
					return new UamRxRange(defs, 817);
				case 818:
					return new UamTxRange(defs, 818);
				case 820:
					return new FormCtrlParam(defs, 820);
				case 821:
					return new FormationEval(defs, 821);
				case 822:
					return new FormationControlParams(defs, 822);
				case 823:
					return new FormationEvaluation(defs, 823);
				case 850:
					return new SoiWaypoint(defs, 850);
				case 851:
					return new SoiPlan(defs, 851);
				case 852:
					return new SoiCommand(defs, 852);
				case 853:
					return new SoiState(defs, 853);
				case 877:
					return new MessagePart(defs, 877);
				case 888:
					return new NeptusBlob(defs, 888);
				case 889:
					return new Aborted(defs, 889);
				case 890:
					return new UsblAngles(defs, 890);
				case 891:
					return new UsblPosition(defs, 891);
				case 892:
					return new UsblFix(defs, 892);
				case 893:
					return new ParametersXml(defs, 893);
				case 894:
					return new GetParametersXml(defs, 894);
				case 895:
					return new SetImageCoords(defs, 895);
				case 896:
					return new GetImageCoords(defs, 896);
				case 897:
					return new GetWorldCoordinates(defs, 897);
				case 898:
					return new UsblAnglesExtended(defs, 898);
				case 899:
					return new UsblPositionExtended(defs, 899);
				case 900:
					return new UsblFixExtended(defs, 900);
				case 901:
					return new UsblModem(defs, 901);
				case 902:
					return new UsblConfig(defs, 902);
				case 903:
					return new DissolvedOrganicMatter(defs, 903);
				case 904:
					return new OpticalBackscatter(defs, 904);
				case 905:
					return new Tachograph(defs, 905);
				case 906:
					return new ApmStatus(defs, 906);
				case 907:
					return new SadcReadings(defs, 907);
				case 908:
					return new DmsDetection(defs, 908);
				case 909:
					return new HomePosition(defs, 909);
				case 910:
					return new ChargingState(defs, 910);
				case 1014:
					return new CurrentProfile(defs, 1014);
				case 1015:
					return new CurrentProfileCell(defs, 1015);
				case 1016:
					return new ADCPBeam(defs, 1016);
				case 2000:
					return new GpioState(defs, 2000);
				case 2001:
					return new GpioStateGet(defs, 2001);
				case 2002:
					return new GpioStateSet(defs, 2002);
				case 2003:
					return new ColoredDissolvedOrganicMatter(defs, 2003);
				case 2004:
					return new FluorescentDissolvedOrganicMatter(defs, 2004);
				case 2005:
					return new IridiumMsgTxExtended(defs, 2005);
				case 2006:
					return new TotalMagIntensity(defs, 2006);
				case 2007:
					return new ValidatePlan(defs, 2007);
				case 2010:
					return new CommRestriction(defs, 2010);
				case 2011:
					return new WifiStats(defs, 2011);
				case 2012:
					return new WifiNetwork(defs, 2012);
				case 2013:
					return new SonarPulse(defs, 2013);
				case 2015:
					return new HealthCheck(defs, 2015);
				case 2016:
					return new QueryTypedEntityParameters(defs, 2016);
				case 2017:
					return new TypedEntityParameter(defs, 2017);
				case 2018:
					return new ValuesIf(defs, 2018);
				case 2019:
					return new DirSonarData(defs, 2019);
				case 2020:
					return new ManeuverResumed(defs, 2020);
				default:
					return new IMCMessage(defs);
			}
		}
		if (65108 == defs.getSyncWord()) {
			switch(mgid) {
				case 1:
					return new EntityState(defs, 1);
				case 2:
					return new QueryEntityState(defs, 2);
				case 3:
					return new EntityInfo(defs, 3);
				case 4:
					return new QueryEntityInfo(defs, 4);
				case 5:
					return new EntityList(defs, 5);
				case 7:
					return new CpuUsage(defs, 7);
				case 8:
					return new TransportBindings(defs, 8);
				case 9:
					return new RestartSystem(defs, 9);
				case 14:
					return new EntityActivationState(defs, 14);
				case 15:
					return new QueryEntityActivationState(defs, 15);
				case 16:
					return new VehicleOperationalLimits(defs, 16);
				case 20:
					return new MsgList(defs, 20);
				case 50:
					return new SimulatedState(defs, 50);
				case 100:
					return new StorageUsage(defs, 100);
				case 101:
					return new CacheControl(defs, 101);
				case 102:
					return new LoggingControl(defs, 102);
				case 103:
					return new LogBookEntry(defs, 103);
				case 104:
					return new LogBookControl(defs, 104);
				case 105:
					return new ReplayControl(defs, 105);
				case 106:
					return new ClockControl(defs, 106);
				case 150:
					return new Heartbeat(defs, 150);
				case 151:
					return new Announce(defs, 151);
				case 152:
					return new AnnounceService(defs, 152);
				case 153:
					return new RSSI(defs, 153);
				case 156:
					return new Sms(defs, 156);
				case 157:
					return new SmsTx(defs, 157);
				case 158:
					return new SmsRx(defs, 158);
				case 159:
					return new SmsState(defs, 159);
				case 160:
					return new TextMessage(defs, 160);
				case 170:
					return new IridiumMsgRx(defs, 170);
				case 171:
					return new IridiumMsgTx(defs, 171);
				case 172:
					return new IridiumTxStatus(defs, 172);
				case 183:
					return new ExtendedRSSI(defs, 183);
				case 200:
					return new LblRange(defs, 200);
				case 202:
					return new LblBeacon(defs, 202);
				case 203:
					return new LblConfig(defs, 203);
				case 211:
					return new AcousticOperation(defs, 211);
				case 212:
					return new AcousticSystemsQuery(defs, 212);
				case 213:
					return new AcousticSystems(defs, 213);
				case 214:
					return new AcousticLink(defs, 214);
				case 250:
					return new Rpm(defs, 250);
				case 251:
					return new Voltage(defs, 251);
				case 252:
					return new Current(defs, 252);
				case 253:
					return new GpsFix(defs, 253);
				case 254:
					return new EulerAngles(defs, 254);
				case 255:
					return new EulerAnglesDelta(defs, 255);
				case 256:
					return new AngularVelocity(defs, 256);
				case 257:
					return new Acceleration(defs, 257);
				case 258:
					return new MagneticField(defs, 258);
				case 259:
					return new GroundVelocity(defs, 259);
				case 260:
					return new WaterVelocity(defs, 260);
				case 261:
					return new VelocityDelta(defs, 261);
				case 262:
					return new Distance(defs, 262);
				case 263:
					return new Temperature(defs, 263);
				case 264:
					return new Pressure(defs, 264);
				case 265:
					return new Depth(defs, 265);
				case 267:
					return new SoundSpeed(defs, 267);
				case 268:
					return new WaterDensity(defs, 268);
				case 269:
					return new Conductivity(defs, 269);
				case 270:
					return new Salinity(defs, 270);
				case 272:
					return new RelativeHumidity(defs, 272);
				case 273:
					return new DevDataText(defs, 273);
				case 274:
					return new DevDataBinary(defs, 274);
				case 276:
					return new SonarData(defs, 276);
				case 277:
					return new Pulse(defs, 277);
				case 278:
					return new PulseDetectionControl(defs, 278);
				case 279:
					return new FuelLevel(defs, 279);
				case 281:
					return new ServoPosition(defs, 281);
				case 282:
					return new DeviceState(defs, 282);
				case 283:
					return new BeamConfig(defs, 283);
				case 284:
					return new DataSanity(defs, 284);
				case 285:
					return new RhodamineDye(defs, 285);
				case 286:
					return new CrudeOil(defs, 286);
				case 287:
					return new FineOil(defs, 287);
				case 288:
					return new Turbidity(defs, 288);
				case 289:
					return new Chlorophyll(defs, 289);
				case 290:
					return new Fluorescein(defs, 290);
				case 291:
					return new Phycocyanin(defs, 291);
				case 292:
					return new Phycoerythrin(defs, 292);
				case 295:
					return new DissolvedOxygen(defs, 295);
				case 296:
					return new AirSaturation(defs, 296);
				case 298:
					return new PH(defs, 298);
				case 299:
					return new Redox(defs, 299);
				case 301:
					return new SetThrusterActuation(defs, 301);
				case 302:
					return new SetServoPosition(defs, 302);
				case 304:
					return new RemoteActionsRequest(defs, 304);
				case 305:
					return new RemoteActions(defs, 305);
				case 306:
					return new ButtonEvent(defs, 306);
				case 307:
					return new LcdControl(defs, 307);
				case 308:
					return new PowerOperation(defs, 308);
				case 309:
					return new PowerChannelControl(defs, 309);
				case 310:
					return new QueryPowerChannelState(defs, 310);
				case 311:
					return new PowerChannelState(defs, 311);
				case 312:
					return new LedBrightness(defs, 312);
				case 313:
					return new QueryLedBrightness(defs, 313);
				case 314:
					return new SetLedBrightness(defs, 314);
				case 315:
					return new SetPWM(defs, 315);
				case 350:
					return new EstimatedState(defs, 350);
				case 351:
					return new EstimatedStreamVelocity(defs, 351);
				case 354:
					return new NavigationUncertainty(defs, 354);
				case 355:
					return new NavigationData(defs, 355);
				case 356:
					return new GpsFixRejection(defs, 356);
				case 357:
					return new LblRangeAcceptance(defs, 357);
				case 358:
					return new DvlRejection(defs, 358);
				case 360:
					return new LblEstimate(defs, 360);
				case 361:
					return new AlignmentState(defs, 361);
				case 400:
					return new DesiredHeading(defs, 400);
				case 401:
					return new DesiredZ(defs, 401);
				case 402:
					return new DesiredSpeed(defs, 402);
				case 403:
					return new DesiredRoll(defs, 403);
				case 404:
					return new DesiredPitch(defs, 404);
				case 406:
					return new DesiredPath(defs, 406);
				case 407:
					return new DesiredControl(defs, 407);
				case 408:
					return new DesiredHeadingRate(defs, 408);
				case 410:
					return new PathControlState(defs, 410);
				case 411:
					return new AllocatedControlTorques(defs, 411);
				case 412:
					return new ControlParcel(defs, 412);
				case 413:
					return new Brake(defs, 413);
				case 450:
					return new Goto(defs, 450);
				case 451:
					return new PopUp(defs, 451);
				case 452:
					return new Teleoperation(defs, 452);
				case 453:
					return new Loiter(defs, 453);
				case 454:
					return new IdleManeuver(defs, 454);
				case 456:
					return new Rows(defs, 456);
				case 457:
					return new FollowPath(defs, 457);
				case 458:
					return new PathPoint(defs, 458);
				case 459:
					return new YoYo(defs, 459);
				case 460:
					return new TeleoperationDone(defs, 460);
				case 461:
					return new StationKeeping(defs, 461);
				case 462:
					return new Elevator(defs, 462);
				case 463:
					return new FollowTrajectory(defs, 463);
				case 464:
					return new TrajectoryPoint(defs, 464);
				case 465:
					return new CustomManeuver(defs, 465);
				case 468:
					return new StopManeuver(defs, 468);
				case 469:
					return new RegisterManeuver(defs, 469);
				case 470:
					return new ManeuverControlState(defs, 470);
				case 474:
					return new PolygonVertex(defs, 474);
				case 475:
					return new CompassCalibration(defs, 475);
				case 478:
					return new FollowReference(defs, 478);
				case 479:
					return new Reference(defs, 479);
				case 480:
					return new FollowRefState(defs, 480);
				case 483:
					return new Dislodge(defs, 483);
				case 485:
					return new Launch(defs, 485);
				case 495:
					return new Alignment(defs, 495);
				case 496:
					return new FollowCommand(defs, 496);
				case 497:
					return new Command(defs, 497);
				case 498:
					return new FollowCommandState(defs, 498);
				case 499:
					return new Magnetometer(defs, 499);
				case 500:
					return new VehicleState(defs, 500);
				case 501:
					return new VehicleCommand(defs, 501);
				case 502:
					return new MonitorEntityState(defs, 502);
				case 503:
					return new EntityMonitoringState(defs, 503);
				case 504:
					return new OperationalLimits(defs, 504);
				case 505:
					return new GetOperationalLimits(defs, 505);
				case 506:
					return new Calibration(defs, 506);
				case 507:
					return new ControlLoops(defs, 507);
				case 508:
					return new VehicleMedium(defs, 508);
				case 509:
					return new Collision(defs, 509);
				case 513:
					return new ReportControl(defs, 513);
				case 515:
					return new AssetReport(defs, 515);
				case 517:
					return new SmsRequest(defs, 517);
				case 518:
					return new SmsStatus(defs, 518);
				case 550:
					return new Abort(defs, 550);
				case 551:
					return new PlanSpecification(defs, 551);
				case 552:
					return new PlanManeuver(defs, 552);
				case 553:
					return new PlanTransition(defs, 553);
				case 556:
					return new PlanDB(defs, 556);
				case 557:
					return new PlanDBState(defs, 557);
				case 558:
					return new PlanDBInformation(defs, 558);
				case 559:
					return new PlanControl(defs, 559);
				case 560:
					return new PlanControlState(defs, 560);
				case 561:
					return new PlanVariable(defs, 561);
				case 562:
					return new PlanGeneration(defs, 562);
				case 564:
					return new PlanStatistics(defs, 564);
				case 606:
					return new CcuEvent(defs, 606);
				case 650:
					return new VehicleLinks(defs, 650);
				case 655:
					return new TrexOperation(defs, 655);
				case 656:
					return new TrexAttribute(defs, 656);
				case 657:
					return new TrexToken(defs, 657);
				case 801:
					return new EntityParameter(defs, 801);
				case 802:
					return new EntityParameters(defs, 802);
				case 803:
					return new QueryEntityParameters(defs, 803);
				case 804:
					return new SetEntityParameters(defs, 804);
				case 805:
					return new SaveEntityParameters(defs, 805);
				case 811:
					return new PushEntityParameters(defs, 811);
				case 812:
					return new PopEntityParameters(defs, 812);
				case 813:
					return new IoEvent(defs, 813);
				case 814:
					return new UamTxFrame(defs, 814);
				case 815:
					return new UamRxFrame(defs, 815);
				case 816:
					return new UamTxStatus(defs, 816);
				case 817:
					return new UamRxRange(defs, 817);
				case 818:
					return new UamTxRange(defs, 818);
				case 877:
					return new MessagePart(defs, 877);
				case 889:
					return new Aborted(defs, 889);
				case 890:
					return new UsblAngles(defs, 890);
				case 891:
					return new UsblPosition(defs, 891);
				case 892:
					return new UsblFix(defs, 892);
				case 898:
					return new UsblAnglesExtended(defs, 898);
				case 899:
					return new UsblPositionExtended(defs, 899);
				case 900:
					return new UsblFixExtended(defs, 900);
				case 901:
					return new UsblModem(defs, 901);
				case 902:
					return new UsblConfig(defs, 902);
				case 903:
					return new DissolvedOrganicMatter(defs, 903);
				case 904:
					return new OpticalBackscatter(defs, 904);
				case 905:
					return new Tachograph(defs, 905);
				case 1014:
					return new CurrentProfile(defs, 1014);
				case 1015:
					return new CurrentProfileCell(defs, 1015);
				case 1016:
					return new ADCPBeam(defs, 1016);
				case 2000:
					return new GpioState(defs, 2000);
				case 2001:
					return new GpioStateGet(defs, 2001);
				case 2002:
					return new GpioStateSet(defs, 2002);
				case 2003:
					return new ColoredDissolvedOrganicMatter(defs, 2003);
				case 2004:
					return new FluorescentDissolvedOrganicMatter(defs, 2004);
				case 2005:
					return new IridiumMsgTxExtended(defs, 2005);
				case 2006:
					return new TotalMagIntensity(defs, 2006);
				case 2007:
					return new ValidatePlan(defs, 2007);
				case 2010:
					return new CommRestriction(defs, 2010);
				case 2011:
					return new WifiStats(defs, 2011);
				case 2012:
					return new WifiNetwork(defs, 2012);
				case 2013:
					return new SonarPulse(defs, 2013);
				case 2014:
					return new ChargingState(defs, 2014);
				case 2015:
					return new HealthCheck(defs, 2015);
				case 2016:
					return new QueryTypedEntityParameters(defs, 2016);
				case 2017:
					return new TypedEntityParameter(defs, 2017);
				case 2018:
					return new ValuesIf(defs, 2018);
				case 2019:
					return new DirSonarData(defs, 2019);
				default:
					return new IMCMessage(defs);
			}
		}
		return new IMCMessage(defs);
}

}
