package com.coderdot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private final String OWNER_EMAIL = "shrutidakhode@gmail.com";

    public void sendBookingEmails(String customerEmail, String username) {

        try {
            sendCustomerEmail(customerEmail, username);
            sendOwnerEmail(customerEmail, username);
            System.out.println("✅ Emails sent");
        } catch (MessagingException e) {
            System.out.println("❌ Email failed: " + e.getMessage());
        }
    }

    // ─── CUSTOMER EMAIL ───────────────────────────────────────────
    private void sendCustomerEmail(String customerEmail, String username) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(customerEmail);
        helper.setFrom("shrutidakhode@gmail.com");
        helper.setSubject("✅ Booking Confirmed — RideBee");

        String html = """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="UTF-8"/>
              <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
            </head>
            <body style="margin:0;padding:0;background:#f4f4f4;font-family:'Segoe UI',Arial,sans-serif;">

              <table width="100%%" cellpadding="0" cellspacing="0" style="background:#f4f4f4;padding:30px 0;">
                <tr>
                  <td align="center">
                    <table width="100%%" cellpadding="0" cellspacing="0"
                      style="max-width:580px;background:#ffffff;border-radius:6px;overflow:hidden;
                             border-top:4px solid #E8720C;">

                      <!-- HEADER -->
                      <tr>
                        <td style="background:#111111;padding:30px 40px;text-align:center;">
                          <h1 style="margin:0;font-size:26px;font-weight:900;
                                     letter-spacing:6px;color:#E8720C;">RIDE<span style="color:#ffffff;">BEE</span></h1>
                          <p style="margin:6px 0 0;font-size:11px;color:#666;letter-spacing:3px;">PREMIUM CAR RENTAL</p>
                        </td>
                      </tr>

                      <!-- SUCCESS BANNER -->
                      <tr>
                        <td style="background:#E8720C;padding:20px 40px;text-align:center;">
                          <p style="margin:0;font-size:13px;font-weight:800;
                                    letter-spacing:4px;color:#000000;">✔ &nbsp; BOOKING CONFIRMED</p>
                        </td>
                      </tr>

                      <!-- BODY -->
                      <tr>
                        <td style="padding:36px 40px;">

                          <p style="margin:0 0 10px;font-size:20px;font-weight:700;color:#111111;">
                            Hello, %s 👋
                          </p>
                          <p style="margin:0 0 28px;font-size:14px;color:#555555;line-height:1.7;">
                            Your car booking has been successfully confirmed.
                            We're thrilled to have you on board — your ride is locked in and ready to go!
                          </p>

                          <!-- INFO BOX -->
                          <table width="100%%" cellpadding="0" cellspacing="0"
                            style="background:#f9f9f9;border:1px solid #eeeeee;
                                   border-left:4px solid #E8720C;border-radius:4px;
                                   margin-bottom:28px;">
                            <tr>
                              <td style="padding:20px 24px;">
                                <p style="margin:0 0 10px;font-size:11px;
                                           letter-spacing:2px;color:#999999;">BOOKING DETAILS</p>
                                <p style="margin:0 0 6px;font-size:14px;color:#111111;">
                                  <strong>Customer:</strong> %s
                                </p>
                                <p style="margin:0 0 6px;font-size:14px;color:#111111;">
                                  <strong>Status:</strong>
                                  <span style="color:#E8720C;font-weight:700;">Confirmed</span>
                                </p>
                                <p style="margin:0;font-size:14px;color:#111111;">
                                  <strong>Service:</strong> RideBee Premium Car Rental
                                </p>
                              </td>
                            </tr>
                          </table>

                          <p style="margin:0 0 28px;font-size:14px;color:#555555;line-height:1.7;">
                            Our team will reach out to you shortly with further details.
                            If you have any questions, feel free to reply to this email.
                          </p>

                          <!-- CTA BUTTON -->
                          <table cellpadding="0" cellspacing="0" style="margin:0 auto 28px;">
                            <tr>
                              <td style="background:#E8720C;padding:14px 36px;
                                         border-radius:3px;text-align:center;">
                                <a href="#" style="color:#000000;font-size:13px;font-weight:800;
                                                   letter-spacing:3px;text-decoration:none;">
                                  VIEW BOOKING
                                </a>
                              </td>
                            </tr>
                          </table>

                          <p style="margin:0;font-size:13px;color:#888888;line-height:1.7;">
                            Thank you for choosing <strong style="color:#E8720C;">RideBee</strong>.
                            We look forward to giving you a premium driving experience. 🚗
                          </p>

                        </td>
                      </tr>

                      <!-- FOOTER -->
                      <tr>
                        <td style="background:#111111;padding:24px 40px;text-align:center;">
                          <p style="margin:0 0 6px;font-size:12px;color:#E8720C;
                                    font-weight:700;letter-spacing:3px;">RIDEBEE</p>
                          <p style="margin:0;font-size:11px;color:#555555;line-height:1.6;">
                            Premium Car Rental Service &nbsp;|&nbsp; support@ridebee.com
                          </p>
                          <p style="margin:10px 0 0;font-size:10px;color:#444444;">
                            © 2026 RideBee. All rights reserved.
                          </p>
                        </td>
                      </tr>

                    </table>
                  </td>
                </tr>
              </table>

            </body>
            </html>
        """.formatted(username, username);

        helper.setText(html, true);
        mailSender.send(message);
    }

    // ─── OWNER EMAIL ──────────────────────────────────────────────
    private void sendOwnerEmail(String customerEmail, String username) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(OWNER_EMAIL);
        helper.setFrom("shrutidakhode@gmail.com");
        helper.setSubject("📩 New Booking Received — RideBee");

        String html = """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="UTF-8"/>
              <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
            </head>
            <body style="margin:0;padding:0;background:#f4f4f4;font-family:'Segoe UI',Arial,sans-serif;">

              <table width="100%%" cellpadding="0" cellspacing="0" style="background:#f4f4f4;padding:30px 0;">
                <tr>
                  <td align="center">
                    <table width="100%%" cellpadding="0" cellspacing="0"
                      style="max-width:580px;background:#ffffff;border-radius:6px;overflow:hidden;
                             border-top:4px solid #E8720C;">

                      <!-- HEADER -->
                      <tr>
                        <td style="background:#111111;padding:30px 40px;text-align:center;">
                          <h1 style="margin:0;font-size:26px;font-weight:900;
                                     letter-spacing:6px;color:#E8720C;">RIDE<span style="color:#ffffff;">BEE</span></h1>
                          <p style="margin:6px 0 0;font-size:11px;color:#666;letter-spacing:3px;">ADMIN NOTIFICATION</p>
                        </td>
                      </tr>

                      <!-- ALERT BANNER -->
                      <tr>
                        <td style="background:#E8720C;padding:20px 40px;text-align:center;">
                          <p style="margin:0;font-size:13px;font-weight:800;
                                    letter-spacing:4px;color:#000000;">📩 &nbsp; NEW BOOKING RECEIVED</p>
                        </td>
                      </tr>

                      <!-- BODY -->
                      <tr>
                        <td style="padding:36px 40px;">

                          <p style="margin:0 0 10px;font-size:20px;font-weight:700;color:#111111;">
                            New Booking Alert
                          </p>
                          <p style="margin:0 0 28px;font-size:14px;color:#555555;line-height:1.7;">
                            A new car booking has just been placed on the RideBee platform.
                            Please review the customer details below and process the booking accordingly.
                          </p>

                          <!-- CUSTOMER DETAILS BOX -->
                          <table width="100%%" cellpadding="0" cellspacing="0"
                            style="background:#f9f9f9;border:1px solid #eeeeee;
                                   border-left:4px solid #E8720C;border-radius:4px;
                                   margin-bottom:28px;">
                            <tr>
                              <td style="padding:20px 24px;">
                                <p style="margin:0 0 14px;font-size:11px;
                                           letter-spacing:2px;color:#999999;">CUSTOMER INFORMATION</p>

                                <table width="100%%" cellpadding="0" cellspacing="0">
                                  <tr>
                                    <td style="padding:6px 0;border-bottom:1px solid #eeeeee;">
                                      <span style="font-size:11px;color:#999;
                                                   letter-spacing:1px;">NAME</span>
                                    </td>
                                    <td style="padding:6px 0;border-bottom:1px solid #eeeeee;
                                               text-align:right;">
                                      <strong style="font-size:14px;color:#111111;">%s</strong>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td style="padding:6px 0;border-bottom:1px solid #eeeeee;">
                                      <span style="font-size:11px;color:#999;
                                                   letter-spacing:1px;">EMAIL</span>
                                    </td>
                                    <td style="padding:6px 0;border-bottom:1px solid #eeeeee;
                                               text-align:right;">
                                      <strong style="font-size:14px;color:#111111;">%s</strong>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td style="padding:6px 0;">
                                      <span style="font-size:11px;color:#999;
                                                   letter-spacing:1px;">STATUS</span>
                                    </td>
                                    <td style="padding:6px 0;text-align:right;">
                                      <span style="font-size:13px;font-weight:800;
                                                   color:#E8720C;">PENDING REVIEW</span>
                                    </td>
                                  </tr>
                                </table>

                              </td>
                            </tr>
                          </table>

                          <p style="margin:0 0 28px;font-size:14px;color:#555555;line-height:1.7;">
                            Please log in to the admin panel to review, approve, or manage this booking.
                            Timely action ensures the best experience for your customer.
                          </p>

                          <!-- CTA BUTTON -->
                          <table cellpadding="0" cellspacing="0" style="margin:0 auto 28px;">
                            <tr>
                              <td style="background:#E8720C;padding:14px 36px;
                                         border-radius:3px;text-align:center;">
                                <a href="#" style="color:#000000;font-size:13px;font-weight:800;
                                                   letter-spacing:3px;text-decoration:none;">
                                  OPEN ADMIN PANEL
                                </a>
                              </td>
                            </tr>
                          </table>

                          <p style="margin:0;font-size:13px;color:#888888;line-height:1.7;">
                            This is an automated notification from the
                            <strong style="color:#E8720C;">RideBee</strong> booking system.
                          </p>

                        </td>
                      </tr>

                      <!-- FOOTER -->
                      <tr>
                        <td style="background:#111111;padding:24px 40px;text-align:center;">
                          <p style="margin:0 0 6px;font-size:12px;color:#E8720C;
                                    font-weight:700;letter-spacing:3px;">RIDEBEE ADMIN</p>
                          <p style="margin:0;font-size:11px;color:#555555;line-height:1.6;">
                            Premium Car Rental Service &nbsp;|&nbsp; support@ridebee.com
                          </p>
                          <p style="margin:10px 0 0;font-size:10px;color:#444444;">
                            © 2026 RideBee. All rights reserved.
                          </p>
                        </td>
                      </tr>

                    </table>
                  </td>
                </tr>
              </table>

            </body>
            </html>
        """.formatted(username, customerEmail);

        helper.setText(html, true);
        mailSender.send(message);
    }
}