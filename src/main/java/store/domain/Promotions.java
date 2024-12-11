package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Promotions {
    private List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Promotion> getPromotions() {
        return new ArrayList<>(promotions);
    }

    public Promotion getPromotionByName(String name) {
        return promotions.stream().filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
