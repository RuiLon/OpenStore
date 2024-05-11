package com.example.bysj.controller;


import com.example.bysj.enity.Borret;
import com.example.bysj.service.imp.BorretServiceImp;
import com.example.bysj.util.RuleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrets")
public class BorretController {

    @Autowired
    private BorretServiceImp borretServiceImp;

    /**
     * 获取所有借还表信息
     * @param borPageNumber
     * @param borretPageSize
     * @param bookname
     * @param borname
     * @param borphone
     * @return
     */
    @GetMapping("/getAllborrets")
    public RuleUtil getAllborrets(@RequestParam Integer borPageNumber,
                                  @RequestParam Integer borretPageSize,
                                  @RequestParam String bookname,
                                  @RequestParam String borname,
                                  @RequestParam String borphone)
    {
        return this.borretServiceImp.getAllborrets(borPageNumber,borretPageSize,bookname,borname,borphone);
    }

    /**
     * 借书
     * @param borret
     * @return
     */
    @PostMapping("/borBook")
    public RuleUtil borBook(@RequestBody(required = false) Borret borret)
    {
        return this.borretServiceImp.borBook(borret);
    }

    /**
     * 还书
     * @param borret
     * @return
     */
    @PutMapping("/retBook")
    public RuleUtil retBook(@RequestBody(required = false) Borret borret)
    {
        return this.borretServiceImp.retBook(borret);
    }

    /**
     * 未还书列表
     * @param borretPageNumber
     * @param borretPageSize
     * @param bookname
     * @param borname
     * @param borphone
     * @return
     */
    @GetMapping("/getAllNoRet")
    public RuleUtil getAllNoRet(@RequestParam Integer borretPageNumber,
                                @RequestParam Integer borretPageSize,
                                @RequestParam String bookname,
                                @RequestParam String borname,
                                @RequestParam String borphone)
    {
        return this.borretServiceImp.getAllNoRet(borretPageNumber,borretPageSize,bookname,borname,borphone);
    }
}
