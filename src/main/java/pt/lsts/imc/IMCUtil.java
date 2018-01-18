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
 */

package pt.lsts.imc;

import pt.lsts.imc.types.PlanSpecificationAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

/**
 * @author zp
 */
public class IMCUtil {
    private static Vector<String> hexFields = new Vector<>();

    static {
        hexFields.add("sync");
        hexFields.add("src");
        hexFields.add("dst");
    }

    /**
     * Generate an HTML representation of the given message
     *
     * @param message The message
     * @return String with the HTML corresponding to given message
     */
    public static String getAsHtml(IMCMessage message) {
        return "<html><h1>" + message.getAbbrev() + "</h1>" +
                getAsInnerHtml(message.getHeader()) + "<br/>" +
                getAsInnerHtml(message) +
                "</html>";
    }

    /**
     * Same as <code>updateMessage(msg, IMCDefinition.getInstance()</code>
     *
     * @param msg The message to be updated to latest IMCDefinition
     * @see #updateMessage(IMCMessage, IMCDefinition)
     */
    public static void updateMessage(IMCMessage msg) {
        updateMessage(msg, IMCDefinition.getInstance());
    }

    /**
     * Update the message to a newer IMCDefinition
     *
     * @param msg    The message to be updated
     * @param target Target IMC definitions. The version should be newer.
     */
    public static void updateMessage(IMCMessage msg, IMCDefinition target) {
        switch (msg.getMgid()) {
            case EulerAngles.ID_STATIC:
                if (msg.getTypeOf("roll") == null && target.getType(EulerAngles.ID_STATIC).getFieldType("roll") != null) {
                    msg.setValue("roll", msg.getDouble("phi"));
                    msg.setValue("pitch", msg.getDouble("theta"));
                    msg.setValue("yaw", msg.getDouble("psi"));
                    msg.setValue("roll_magnetic", msg.getDouble("phi_magnetic"));
                    msg.setValue("pitch_magnetic", msg.getDouble("theta_magnetic"));
                    msg.setValue("yaw_magnetic", msg.getDouble("psi_magnetic"));
                }
                break;
            case LblConfig.ID_STATIC:
                if (msg.getTypeOf("beacon0") != null
                        && target.getType(LblConfig.ID_STATIC).getFieldType("beacons") != null) {
                    Vector<IMCMessage> beacons = new Vector<>();
                    for (String id : new String[]{"beacon0", "beacon1", "beacon2", "beacon3", "beacon4", "beacon5"}) {
                        IMCMessage m = (msg.getMessage(id));
                        if (m != null)
                            beacons.add(m);
                    }
                    msg.setValue("beacons", beacons);
                }
                break;
            case SonarData.ID_STATIC:
                if (msg.getTypeOf("min_range") == null
                        && target.getType(SonarData.ID_STATIC).getFieldType("min_range") != null) {
                    msg.setValue("min_range", msg.getDouble("range"));
                    msg.setValue("max_range", msg.getDouble("range"));
                }
            case FollowTrajectory.ID_STATIC:
            case FollowPath.ID_STATIC:
            case VehicleFormation.ID_STATIC:
                if (msg.getTypeOf("points").equals("message")
                        && target.getType(FollowPath.ID_STATIC).getFieldType("points") == IMCFieldType.TYPE_MESSAGELIST) {
                    Vector<IMCMessage> points = new Vector<>();
                    IMCMessage curr = msg.getMessage("points");
                    while (curr != null) {
                        IMCMessage prev = curr;
                        points.add(curr);
                        curr = curr.getMessage("point");
                        prev.setType(target.getType(prev.getMgid()));
                    }
                    msg.setValue("points", points);
                }
                break;
            case PlanSpecification.ID_STATIC:
                PlanSpecificationAdapter plan = new PlanSpecificationAdapter(msg);
                try {
                    msg.copyFrom(plan.getData(target));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        if (target.getType(msg.getMgid()) != null) {
            msg.setType(target.getType(msg.getMgid()));
        }
    }

    private static String getAsInnerHtml(IMCMessage msg) {
        if (msg == null)
            return "null";

        StringBuilder ret = new StringBuilder("<table border=1><tr bgcolor='#CCCCEE'><th>" + msg.getAbbrev() + "</th><th>" + msg.getFieldNames().length + " fields</th></tr>");
        if (msg.getAbbrev() == null)
            ret = new StringBuilder("<table border=1 width=100%><tr bgcolor='#CCEECC'><th>Header</th><th>" + msg.getFieldNames().length + " fields</th></tr>");

        for (String fieldName : msg.getFieldNames()) {
            StringBuilder value = new StringBuilder(msg.getString(fieldName));
            if (msg.getAbbrev() == null && fieldName.equals("timestamp")) {
                SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss.SSS ");
                dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
                value = new StringBuilder(dateFormatUTC.format(new Date((long) (msg.getDouble("timestamp") * 1000.0))) + "UTC");
            } else if (msg.getAbbrev() == null && hexFields.contains(fieldName)) {
                value.append("  [0x").append(Long.toHexString(msg.getLong(fieldName)).toUpperCase()).append("]");
            }

            if (msg.getTypeOf(fieldName).equalsIgnoreCase("message") && msg.getValue(fieldName) != null)
                value = new StringBuilder(getAsInnerHtml(msg.getMessage(fieldName)));

            else if (msg.getTypeOf(fieldName).equalsIgnoreCase("message-list") && msg.getValue(fieldName) != null) {
                value = new StringBuilder("<table><tr>");
                for (IMCMessage m : msg.getMessageList(fieldName))
                    value.append("<td>").append(getAsInnerHtml(m)).append("</td>");

                value.append("</tr></table>");
            }

            ret.append("<tr><td align=center width=225>").append(fieldName).append("</td><td width=225>").append(value).append("</td></tr>");
        }
        return ret + "</table>";
    }
}
