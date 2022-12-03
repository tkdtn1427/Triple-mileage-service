package trip.milliage.entity.attachedPhoto;

import lombok.*;
import trip.milliage.common.Auditable;
import trip.milliage.entity.review.Review;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttachedPhoto extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "Binary(16)")
    private UUID photoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REVIEW_ID")
    private Review review;

    @Builder
    public AttachedPhoto(UUID attachedPhotoId, Review review){
        this.photoId = attachedPhotoId;
        this.review = review;
    }

    public void setReview(Review review){
        this.review = review;
    }
}
