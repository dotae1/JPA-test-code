package org.zerock.ex2.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test //실체 객체가 어떤것인지
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    @Test //Insert
    public void testInseerDummies() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." +i).build();
            memoRepository.save(memo);
        });
    }

    @Test //조회작업
    public void testSelect() {
        //데베에 존재하는 mno
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("=====================================");

        if(result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Test // 수정작업
    public void testUpdate() {
        Memo memo = Memo.builder().mno(100L).memoText("Upadte Text").build();

        System.out.println(memoRepository.save(memo));
    }

    @Test //삭제작업
    public void testDelete() {
        Long mno = 100L;

        memoRepository.deleteById(mno);
    }

    @Test //페이징 처리
    public void testPageDefault() {

        //1페이지 10개
        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("---------------------------------------------------");

        System.out.println("Total Pages: " + result.getTotalPages()); //총 몇 페이지

        System.out.println("Total count: " + result.getTotalElements());//전체 개수

        System.out.println("Page Number: " + result.getNumber()); //현재 페이지 번호

        System.out.println("Page Size: " + result.getSize()); //페이지당 데이터 개수

        System.out.println("has next Page: " + result.hasNext()); //다음 페이지 존재 여부

        System.out.println("first page? " + result.isFirst()); //시작 페이지 여부

        System.out.println("=====================================");

        for(Memo memo: result.getContent()) {
            System.out.println(memo);
        }
    }

    @Test // 정렬 조건 추가
    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        Pageable pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testQueryMethods() {
        List<Memo> list = memoRepository.
                findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for(Memo memo: list) {
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWIthPagable() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);

        result.get().forEach(memo -> System.out.println(memo));
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteQueryMethods() {
        memoRepository.deleteMemoByMnoLessThan(10L);
    }
}
