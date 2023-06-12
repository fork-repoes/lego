package com.geekhalo.tinyurl;

import com.geekhalo.tinyurl.app.TinyUrlCommandApplicationService;
import com.geekhalo.tinyurl.app.TinyUrlQueryApplicationService;
import com.geekhalo.tinyurl.domain.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = Application.class)
public class ApplicationTest {
    @Autowired
    private TinyUrlCommandApplicationService commandApplicationService;

    @Autowired
    private TinyUrlQueryApplicationService tinyUrlQueryApplicationService;

    @Test
    public void createTinyUrl(){
        String url = "http://createTinyUrl";
        CreateTinyUrlCommand command = new CreateTinyUrlCommand();
        command.setUrl(url);
        TinyUrl tinyUrl = this.commandApplicationService.createTinyUrl(command);
        Assert.assertNotNull(tinyUrl);
        Assert.assertNotNull(tinyUrl.getId());

        Optional<TinyUrl> tinyUrlOptional = this.tinyUrlQueryApplicationService.findById(tinyUrl.getId());
        TinyUrl tinyUrlToCheck = tinyUrlOptional.orElse(null);
        Assert.assertNotNull(tinyUrlToCheck);
        Assert.assertEquals(tinyUrl.getId(), tinyUrlToCheck.getId());
        Assert.assertEquals(tinyUrl.getUrl(), tinyUrlToCheck.getUrl());
        Assert.assertEquals(TinyUrlType.COMMON, tinyUrlToCheck.getType());
        Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrlToCheck.getStatus());
        Assert.assertTrue(tinyUrlToCheck.canAccess());

    }

    @Test
    public void createExpireTimeTinyUrl() throws Exception{
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        String url = "http://createExpireTimeTinyUrl";
        Date beginTime = DateUtils.addSeconds(new Date(), 2);
        Date expireTime = DateUtils.addSeconds(new Date(), 5);
        CreateExpireTimeTinyUrlCommand command = new CreateExpireTimeTinyUrlCommand();
        command.setUrl(url);
        command.setBeginTime(beginTime);
        command.setExpireTime(expireTime);
        TinyUrl tinyUrl = this.commandApplicationService.createExpireTimeTinyUrl(command);
        Assert.assertNotNull(tinyUrl);
        Assert.assertNotNull(tinyUrl.getId());

        {
            Optional<TinyUrl> tinyUrlOptional = this.tinyUrlQueryApplicationService.findById(tinyUrl.getId());
            TinyUrl tinyUrlToCheck = tinyUrlOptional.orElse(null);
            Assert.assertNotNull(tinyUrlToCheck);
            Assert.assertEquals(tinyUrl.getId(), tinyUrlToCheck.getId());
            Assert.assertEquals(tinyUrl.getUrl(), tinyUrlToCheck.getUrl());
            Assert.assertEquals(TinyUrlType.EXPIRE_TIME, tinyUrlToCheck.getType());
            Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrlToCheck.getStatus());
            Assert.assertEquals(DateFormatUtils.format(beginTime,dateFormat), DateFormatUtils.format(tinyUrlToCheck.getBeginTime(), dateFormat));
            Assert.assertEquals(DateFormatUtils.format(expireTime,dateFormat), DateFormatUtils.format(tinyUrlToCheck.getExpireTime(), dateFormat));
            Assert.assertFalse(tinyUrlToCheck.canAccess());
        }

        TimeUnit.SECONDS.sleep(3);

        {
            Optional<TinyUrl> tinyUrlOptional = this.tinyUrlQueryApplicationService.findById(tinyUrl.getId());
            TinyUrl tinyUrlToCheck = tinyUrlOptional.orElse(null);
            Assert.assertNotNull(tinyUrlToCheck);
            Assert.assertEquals(tinyUrl.getId(), tinyUrlToCheck.getId());
            Assert.assertEquals(tinyUrl.getUrl(), tinyUrlToCheck.getUrl());
            Assert.assertEquals(TinyUrlType.EXPIRE_TIME, tinyUrlToCheck.getType());
            Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrlToCheck.getStatus());
            Assert.assertEquals(DateFormatUtils.format(beginTime,dateFormat), DateFormatUtils.format(tinyUrlToCheck.getBeginTime(), dateFormat));
            Assert.assertEquals(DateFormatUtils.format(expireTime,dateFormat), DateFormatUtils.format(tinyUrlToCheck.getExpireTime(), dateFormat));
            Assert.assertTrue(tinyUrlToCheck.canAccess());
        }

        TimeUnit.SECONDS.sleep(3);

        {
            Optional<TinyUrl> tinyUrlOptional = this.tinyUrlQueryApplicationService.findById(tinyUrl.getId());
            TinyUrl tinyUrlToCheck = tinyUrlOptional.orElse(null);
            Assert.assertNotNull(tinyUrlToCheck);
            Assert.assertEquals(tinyUrl.getId(), tinyUrlToCheck.getId());
            Assert.assertEquals(tinyUrl.getUrl(), tinyUrlToCheck.getUrl());
            Assert.assertEquals(TinyUrlType.EXPIRE_TIME, tinyUrlToCheck.getType());
            Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrlToCheck.getStatus());
            Assert.assertEquals(DateFormatUtils.format(beginTime,dateFormat), DateFormatUtils.format(tinyUrlToCheck.getBeginTime(), dateFormat));
            Assert.assertEquals(DateFormatUtils.format(expireTime,dateFormat), DateFormatUtils.format(tinyUrlToCheck.getExpireTime(), dateFormat));
            Assert.assertFalse(tinyUrlToCheck.canAccess());
        }
    }

    @Test
    public void createLimitTimeTinyUrl(){
        String url = "http://createLimitTimeTinyUrl";
        Integer maxCount = 3;
        CreateLimitTimeTinyUrlCommand command = new CreateLimitTimeTinyUrlCommand();
        command.setUrl(url);
        command.setMaxCount(maxCount);
        TinyUrl tinyUrl = this.commandApplicationService.createLimitTimeTinyUrl(command);

        Assert.assertNotNull(tinyUrl);
        Assert.assertNotNull(tinyUrl.getId());

        {
            Optional<TinyUrl> tinyUrlOptional = this.tinyUrlQueryApplicationService.findById(tinyUrl.getId());
            TinyUrl tinyUrlToCheck = tinyUrlOptional.orElse(null);
            Assert.assertNotNull(tinyUrlToCheck);
            Assert.assertEquals(tinyUrl.getId(), tinyUrlToCheck.getId());
            Assert.assertEquals(tinyUrl.getUrl(), tinyUrlToCheck.getUrl());
            Assert.assertEquals(TinyUrlType.LIMIT_TIME, tinyUrlToCheck.getType());
            Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrlToCheck.getStatus());
            Assert.assertEquals(maxCount, tinyUrlToCheck.getMaxCount());
            Assert.assertEquals(0, tinyUrlToCheck.getAccessCount().intValue());
            Assert.assertTrue(tinyUrlToCheck.canAccess());
        }
    }

    @Test
    public void incrAccessCount(){
        String url = "http://createLimitTimeTinyUrl";
        Integer maxCount = 3;
        CreateLimitTimeTinyUrlCommand command = new CreateLimitTimeTinyUrlCommand();
        command.setUrl(url);
        command.setMaxCount(maxCount);
        TinyUrl tinyUrl = this.commandApplicationService.createLimitTimeTinyUrl(command);

        Assert.assertNotNull(tinyUrl);
        Assert.assertNotNull(tinyUrl.getId());

        {
            Optional<TinyUrl> tinyUrlOptional = this.tinyUrlQueryApplicationService.findById(tinyUrl.getId());
            TinyUrl tinyUrlToCheck = tinyUrlOptional.orElse(null);
            Assert.assertNotNull(tinyUrlToCheck);
            Assert.assertEquals(tinyUrl.getId(), tinyUrlToCheck.getId());
            Assert.assertEquals(tinyUrl.getUrl(), tinyUrlToCheck.getUrl());
            Assert.assertEquals(TinyUrlType.LIMIT_TIME, tinyUrlToCheck.getType());
            Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrlToCheck.getStatus());
            Assert.assertEquals(maxCount, tinyUrlToCheck.getMaxCount());
            Assert.assertEquals(0, tinyUrlToCheck.getAccessCount().intValue());
            Assert.assertTrue(tinyUrlToCheck.canAccess());
        }

        IncrAccessCountCommand incrAccessCountCommand = new IncrAccessCountCommand();
        incrAccessCountCommand.setId(tinyUrl.getId());
        incrAccessCountCommand.setIncrCount(maxCount);
        this.commandApplicationService.incrAccessCount(incrAccessCountCommand);

        {
            Optional<TinyUrl> tinyUrlOptional = this.tinyUrlQueryApplicationService.findById(tinyUrl.getId());
            TinyUrl tinyUrlToCheck = tinyUrlOptional.orElse(null);
            Assert.assertNotNull(tinyUrlToCheck);
            Assert.assertEquals(tinyUrl.getId(), tinyUrlToCheck.getId());
            Assert.assertEquals(tinyUrl.getUrl(), tinyUrlToCheck.getUrl());
            Assert.assertEquals(TinyUrlType.LIMIT_TIME, tinyUrlToCheck.getType());
            Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrlToCheck.getStatus());
            Assert.assertEquals(maxCount, tinyUrlToCheck.getMaxCount());
            Assert.assertEquals(maxCount.intValue(), tinyUrlToCheck.getAccessCount().intValue());
            Assert.assertFalse(tinyUrlToCheck.canAccess());
        }
    }
    @Test
    public void disableTinyUrl(){
        String url = "http://createTinyUrl";
        CreateTinyUrlCommand command = new CreateTinyUrlCommand();
        command.setUrl(url);
        TinyUrl tinyUrl = this.commandApplicationService.createTinyUrl(command);
        Assert.assertNotNull(tinyUrl);
        Assert.assertNotNull(tinyUrl.getId());

        {
            Optional<TinyUrl> tinyUrlOptional = this.tinyUrlQueryApplicationService.findById(tinyUrl.getId());
            TinyUrl tinyUrlToCheck = tinyUrlOptional.orElse(null);
            Assert.assertNotNull(tinyUrlToCheck);
            Assert.assertEquals(tinyUrl.getId(), tinyUrlToCheck.getId());
            Assert.assertEquals(tinyUrl.getUrl(), tinyUrlToCheck.getUrl());
            Assert.assertEquals(TinyUrlType.COMMON, tinyUrlToCheck.getType());
            Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrlToCheck.getStatus());
            Assert.assertTrue(tinyUrlToCheck.canAccess());
        }

        DisableTinyUrlCommand disableTinyUrlCommand = new DisableTinyUrlCommand();
        disableTinyUrlCommand.setId(tinyUrl.getId());
        this.commandApplicationService.disableTinyUrl(disableTinyUrlCommand);
        {
            Optional<TinyUrl> tinyUrlOptional = this.tinyUrlQueryApplicationService.findById(tinyUrl.getId());
            TinyUrl tinyUrlToCheck = tinyUrlOptional.orElse(null);
            Assert.assertNotNull(tinyUrlToCheck);
            Assert.assertEquals(tinyUrl.getId(), tinyUrlToCheck.getId());
            Assert.assertEquals(tinyUrl.getUrl(), tinyUrlToCheck.getUrl());
            Assert.assertEquals(TinyUrlType.COMMON, tinyUrlToCheck.getType());
            Assert.assertEquals(TinyUrlStatus.DISABLE, tinyUrlToCheck.getStatus());
            Assert.assertFalse(tinyUrlToCheck.canAccess());
        }
    }
}
