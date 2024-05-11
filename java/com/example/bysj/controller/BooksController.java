package com.example.bysj.controller;

import com.example.bysj.enity.Books;
import com.example.bysj.service.imp.BooksServiceImp;
import com.example.bysj.util.RuleUtil;
import org.apache.tomcat.util.digester.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {


    @Autowired
    private BooksServiceImp booksServiceImp;

    /**
     * 获取所有书籍
     * @param PageNumber
     * @param PageSize
     * @param bname
     * @param bscode
     * @param bauther
     * @return
     */
    @GetMapping("/getAllBooks")
    public RuleUtil getAllBooks(@RequestParam Integer PageNumber,
                                @RequestParam Integer PageSize,
                                @RequestParam String bname,
                                @RequestParam String bscode,
                                @RequestParam String bauther)
    {
        return this.booksServiceImp.getAllBooks(PageNumber,PageSize,bname,bscode,bauther);
    }

    @PutMapping("/updatebooks")
    public RuleUtil updatebooks(@RequestBody(required = false) Books books)
    {
        return this.booksServiceImp.updateBooks(books);
    }

    /**
     * 删除书籍
     * @param books
     * @return
     */
    @PutMapping("/delBooks")
    public RuleUtil delBooks(@RequestBody(required = false) Books books)
    {
        return this.booksServiceImp.delBooks(books);
    }

    /**
     * 批量删除书籍
     * @param delList
     * @return
     */
    @PutMapping("/delListBooks")
    public RuleUtil delListBooks(@RequestBody List<Books> delList)
    {
        return this.booksServiceImp.delList(delList);
    }

    @PostMapping("/addBooks")
    public RuleUtil addBooks(@RequestBody(required = false) Books books)
    {
        return this.booksServiceImp.addBooks(books);
    }
}
