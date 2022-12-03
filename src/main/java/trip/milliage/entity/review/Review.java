package trip.milliage.entity.review;

import lombok.*;
import trip.milliage.common.Auditable;
import trip.milliage.entity.attachedPhoto.AttachedPhoto;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends Auditable {
    @Id
    @Column(columnDefinition = "Binary(16)")
    private UUID reviewId;

    @Column(columnDefinition = "Binary(16)")
    private UUID userId;

    @Column
    private String content;

    @Column(columnDefinition = "Binary(16)")
    private UUID placeId;

    @Column
    private boolean isBonus = false;

    @OneToMany(mappedBy = "review",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AttachedPhoto> attachedPhotoList = new HashSet<>();

    @Builder
    public Review(UUID id, UUID userId, String content, UUID placeId){
        this.reviewId = id;
        this.userId = userId;
        this.content = content;
        this.placeId = placeId;
    }

    public void addPhoto(AttachedPhoto attachedPhoto){
        this.attachedPhotoList.add(attachedPhoto);
        if(attachedPhoto.getReview() != this){
            attachedPhoto.setReview(this);
        }
    }

    public void deletePhoto(AttachedPhoto attachedPhoto) {
        this.attachedPhotoList.remove(attachedPhoto);
        attachedPhoto.setReview(null);
    }

    public boolean getBonus(){
        return this.isBonus;
    }
}
