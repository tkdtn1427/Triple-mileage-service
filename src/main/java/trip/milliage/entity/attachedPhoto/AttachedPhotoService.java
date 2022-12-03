package trip.milliage.entity.attachedPhoto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trip.milliage.entity.review.Review;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AttachedPhotoService {
    private final AttachedPhtoRepository attachedPhtoRepository;

    public AttachedPhotoService(AttachedPhtoRepository attachedPhtoRepository) {
        this.attachedPhtoRepository = attachedPhtoRepository;
    }

    public AttachedPhoto findAttachedPhoto(Review review, UUID photoId){
        Optional<AttachedPhoto> attachedPhoto = attachedPhtoRepository.existByReviewIddAndPhoto(review.getReviewId(), photoId);
        if(attachedPhoto.isEmpty()){
            return new AttachedPhoto(photoId, review);
        }
        return attachedPhoto.get();
    }
}
