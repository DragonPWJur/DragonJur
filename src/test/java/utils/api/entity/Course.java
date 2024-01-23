package utils.api.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {

    private String createdAt;
    private String updatedAt;
    private String id;
    private String releasedAt;
    private String name;
    private String description;
    private String directionId;
    private Integer flashcardsWeight;
    private Integer assignmentsWeight;
    private Integer testTopicsWeight;
    private String leastKnownCategory;
    private String domainButtonName;
    private String chapterButtonName;
    private String domainStatsButtonName;
    private String chapterStatsButtonName;
    private String activeCampaignAutomationId;
    private Price prices;
    private Integer amountChaptersAtStudyGuide;
    private Integer amountQuestions;
    private Integer amountMnemonicCardsPacks;
    private Integer amountFlashcardsPacks;
    private Boolean isActive;
    private CurrentPayment currentPayment;

}