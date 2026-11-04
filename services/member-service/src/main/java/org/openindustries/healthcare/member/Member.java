package org.openindustries.healthcare.member;

import java.time.Instant;
import java.util.UUID;

/**
 * Member aggregate — Member registry; the member number is masked at enrolment and never kept in clear.
 *
 * Privacy by design (ISO/IEC 27002 A.5.34): secret identifiers are validated at
 * creation and NEVER persisted or returned in clear — only a masked form is kept.
 * All identifiers in this reference implementation are synthetic.
 */
public class Member {
    public UUID id;
        public String fullName;
        public String memberNumberMasked;
        public String planCode;
    public String status;
    public Instant createdAt;

    public static Member create(String fullName, String memberNumber, String planCode) {
        Member e = new Member();
        e.id = UUID.randomUUID();
        e.fullName = fullName;
        e.memberNumberMasked = mask(memberNumber);
        e.planCode = planCode;
        e.status = "ACTIVE";
        e.createdAt = Instant.now();
        return e;
    }

    static String mask(String value) {
        if (value == null || value.length() < 4) {
            return "****";
        }
        return "*".repeat(value.length() - 4) + value.substring(value.length() - 4);
    }
}
