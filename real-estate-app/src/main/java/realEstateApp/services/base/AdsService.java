package realEstateApp.services.base;

import realEstateApp.models.Ads;

import java.util.List;

public interface AdsService {
    List<Ads> findAll();

    List<Ads> findLatest5();

    Ads findById(Long id);

    Ads create(Ads ads);

    Ads edit(Ads ads);

    void deleteById(Long id);
}