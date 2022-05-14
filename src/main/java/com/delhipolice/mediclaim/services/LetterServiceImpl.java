package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.model.Letter;
import com.delhipolice.mediclaim.repositories.LetterRepository;
import com.delhipolice.mediclaim.vo.DiaryEntryVO;
import com.delhipolice.mediclaim.vo.LetterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LetterServiceImpl implements LetterService{

    @Autowired
    LetterRepository letterRepository;

    @Autowired
    DiaryEntryService diaryEntryService;

    @Override
    public Letter find(UUID id) {
        return letterRepository.findById(id).orElse(new Letter());
    }

    @Override
    public List<LetterVO> findAll() {
        return letterRepository.findAll().stream().map(LetterVO::new).collect(Collectors.toList());
    }

    @Override
    public Letter save(LetterVO letterVO) {
        updateDiaryEntries(letterVO.getDiaryEntryVOS());
        return letterRepository.save(new Letter(letterVO));
    }

    @Override
    public Letter save(List<UUID> uuids) {
        List<DiaryEntry> diaryEntries = diaryEntryService.findAllByUUIDs(uuids);
        for(DiaryEntry diaryEntry : diaryEntries) {
            diaryEntry.setIsLetterGenerated(true);
            diaryEntryService.update(diaryEntry);
        }
        Letter letter = new Letter(diaryEntries);
        return letterRepository.save(letter);
    }

    private void updateDiaryEntries(List<DiaryEntryVO> diaryEntryVO) {
        for(DiaryEntryVO diaryEntry : diaryEntryVO) {
            diaryEntry.setIsLetterGenerated(true);
            diaryEntryService.update(diaryEntry);
        }
    }

    @Override
    public Letter update(LetterVO letterVO) {
        Letter letter = letterRepository.findById(letterVO.getId()).orElse(new Letter());
        letter.setLetterDate(letterVO.getLetterDate());

        for(DiaryEntry diaryEntry : letter.getDiaryEntries()) {
            diaryEntry.setIsLetterGenerated(false);
            diaryEntryService.update(diaryEntry);
        }

        List<DiaryEntry> diaryEntries = diaryEntryService.findAll(letterVO.getDiaryEntryVOS());
        updateDiaryEntries(diaryEntries.stream().map(DiaryEntryVO::new).collect(Collectors.toList()));
        letter.setDiaryEntries(diaryEntries);

        return letterRepository.save(new Letter(letterVO));
    }
}
