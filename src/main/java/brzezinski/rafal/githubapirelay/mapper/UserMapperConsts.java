package brzezinski.rafal.githubapirelay.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapperConsts {
    public static final BigDecimal DEFAULT_CALCULATIONS_VALUE = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    public static final BigDecimal NUMERATOR = BigDecimal.valueOf(6).setScale(2, RoundingMode.HALF_UP);
    public static final int ADDEND_FOR_PUBLIC_REPOS = 2;
}
