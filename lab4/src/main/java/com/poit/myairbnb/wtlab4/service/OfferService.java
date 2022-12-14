package com.poit.myairbnb.wtlab4.service;

import com.poit.myairbnb.wtlab4.entity.Offer;
import com.poit.myairbnb.wtlab4.repository.OfferRepository;
import java.sql.SQLException;
import java.util.List;

public class OfferService {
    private final OfferRepository offerRepository = new OfferRepository();

    public void save(Offer offer) {
        try {
            offerRepository.save(offer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Offer> findAll() {
        try {
            return offerRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
