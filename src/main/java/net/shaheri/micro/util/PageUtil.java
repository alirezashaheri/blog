package net.shaheri.micro.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageUtil {
    public <T> Page<T> toPage (List<T> objects, int pageSize, int pageNumber){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        int start = Math.toIntExact(pageRequest.getOffset());
        int end = Math.min((start + pageRequest.getPageSize()), objects.size());
        return new PageImpl<>(objects.subList(start, end), pageRequest, objects.size());
    }
}
