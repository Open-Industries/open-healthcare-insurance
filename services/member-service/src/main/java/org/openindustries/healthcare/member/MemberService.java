package org.openindustries.healthcare.member;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MemberService {

    @Inject
    MemberRepository repository;

    public Member create(CreateMemberRequest request) {
        return repository.save(Member.create(request.fullName(), request.memberNumber(), request.planCode()));
    }

    public Member get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Member not found: " + id));
    }

    public List<Member> list() {
        return repository.findAll();
    }

    public Member suspend(UUID id) {
        Member entity = get(id);
        if ("TERMINATED".equals(entity.status)) {
            throw new DomainException(409, "A terminated member is terminal");
        }
        if ("SUSPENDED".equals(entity.status)) {
            throw new DomainException(409, "Member already SUSPENDED");
        }
        entity.status = "SUSPENDED";
        return repository.save(entity);
    }

    public Member terminate(UUID id) {
        Member entity = get(id);
        if ("TERMINATED".equals(entity.status)) {
            throw new DomainException(409, "A terminated member is terminal");
        }
        if ("TERMINATED".equals(entity.status)) {
            throw new DomainException(409, "Member already TERMINATED");
        }
        entity.status = "TERMINATED";
        return repository.save(entity);
    }
}
