package com.jobconnect.common.constants;

public final class AppConstants {

    private AppConstants() {
        // Prevent instantiation
    }

    // Email subjects
    public static final String WELCOME_SUBJECT = "Welcome to JobHunt!";
    public static final String OTP_SUBJECT = "Your OTP for JobHunt";
    public static final String JOB_APPLICATION_SUBJECT = "Application Submitted Successfully";
    public static final String STATUS_UPDATE_SUBJECT = "Your Application Status Update";

    // Email templates
    public static String getWelcomeEmailBody(String firstName, String profileType) {
        return String.format("""
            Dear %s,
            
            Welcome to JobHunt! We're thrilled to have you as part of our %s community.
            
            Your account has been successfully created. Now you can:
            - Browse jobs and apply (for Job Seekers)
            - Post jobs and find talent (for Recruiters)
            
            If you have any questions, feel free to contact our support team.
            
            Best Regards,
            JobHunt Team
            """, firstName, profileType);
    }

    public static String getOtpEmailBody(String firstName, String otp) {
        return String.format("""
            Dear %s,
            
            Your OTP for verification is: %s
            This OTP is valid for 10 minutes.
            
            Please do not share this OTP with anyone for security reasons.
            
            Best Regards,
            JobHunt Team
            """, firstName, otp);
    }

    public static String getJobApplicationEmailBody(String firstName, String jobTitle, String companyName) {
        return String.format("""
            Dear %s,
            
            Thank you for applying for the position of %s at %s.
            Your application has been received successfully.
            
            We'll review your application and get back to you soon.
            You can check your application status in your dashboard.
            
            Best Regards,
            JobHunt Team
            """, firstName, jobTitle, companyName);
    }

    public static String getStatusUpdateEmailBody(String firstName, String jobTitle,
                                                  String oldStatus, String newStatus) {
        return String.format("""
            Dear %s,
            
            Your application status for %s has been updated:
            From: %s
            To: %s
            
            %s
            
            Best Regards,
            JobHunt Team
            """, firstName, jobTitle, oldStatus, newStatus, getStatusUpdateMessage(newStatus));
    }

    private static String getStatusUpdateMessage(String newStatus) {
        return switch (newStatus) {
            case "APPLIED" -> "Your application has been received and is under review.";
            case "SHORTLISTED" -> "Congratulations! Your application has been shortlisted. "
                    + "The recruiter may contact you for next steps.";
            case "REJECTED" -> "We regret to inform you that your application wasn't selected "
                    + "for this position. We encourage you to apply for other opportunities.";
            case "HIRED" -> "Fantastic news! You've been selected for this position. "
                    + "The recruiter will contact you with offer details shortly.";
            default -> "Please check your dashboard for more details.";
        };
    }

}