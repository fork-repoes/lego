package com.geekhalo.tinyurl;

import com.geekhalo.tinyurl.app.TinyUrlCommandApplicationService;
import com.geekhalo.tinyurl.app.TinyUrlQueryApplicationService;
import com.geekhalo.tinyurl.domain.CreateTinyUrlCommand;
import com.geekhalo.tinyurl.domain.TinyUrl;
import com.geekhalo.tinyurl.domain.TinyUrlStatus;
import com.geekhalo.tinyurl.domain.TinyUrlType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest(classes = Application.class)
public class ApplicationForCacheTest {
    @Autowired
    private TinyUrlCommandApplicationService commandApplicationService;

    @Autowired
    private TinyUrlQueryApplicationService tinyUrlQueryApplicationService;

    @Test
    public void createTinyUrl_NoCache(){
        String url = "http://createTinyUrl";
        CreateTinyUrlCommand command = new CreateTinyUrlCommand();
        command.setUrl(url);
        TinyUrl tinyUrl = this.commandApplicationService.createTinyUrl(command);
        Assert.assertNotNull(tinyUrl);
        Assert.assertNotNull(tinyUrl.getId());

        for (int i = 0; i< 100; i++) {
            Optional<TinyUrl> tinyUrlOptional = this.tinyUrlQueryApplicationService.findById(tinyUrl.getId());
            TinyUrl tinyUrlToCheck = tinyUrlOptional.orElse(null);
            Assert.assertNotNull(tinyUrlToCheck);
            Assert.assertEquals(tinyUrl.getId(), tinyUrlToCheck.getId());
            Assert.assertEquals(tinyUrl.getUrl(), tinyUrlToCheck.getUrl());
            Assert.assertEquals(TinyUrlType.COMMON, tinyUrlToCheck.getType());
            Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrlToCheck.getStatus());
            Assert.assertTrue(tinyUrlToCheck.canAccess());
        }
    }

    @Test
    public void createTinyUrl_Cache(){
        String url = "http://createTinyUrl";
        CreateTinyUrlCommand command = new CreateTinyUrlCommand();
        command.setUrl(url);
        command.setEnableCache(true);
        TinyUrl tinyUrl = this.commandApplicationService.createTinyUrl(command);
        Assert.assertNotNull(tinyUrl);
        Assert.assertNotNull(tinyUrl.getId());

        for (int i = 0; i< 100; i++) {
            Optional<TinyUrl> tinyUrlOptional = this.tinyUrlQueryApplicationService.findById(tinyUrl.getId());
            TinyUrl tinyUrlToCheck = tinyUrlOptional.orElse(null);
            Assert.assertNotNull(tinyUrlToCheck);
            Assert.assertEquals(tinyUrl.getId(), tinyUrlToCheck.getId());
            Assert.assertEquals(tinyUrl.getUrl(), tinyUrlToCheck.getUrl());
            Assert.assertEquals(TinyUrlType.COMMON, tinyUrlToCheck.getType());
            Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrlToCheck.getStatus());
            Assert.assertTrue(tinyUrlToCheck.canAccess());
        }
    }

    @Test
    public void createTinyUrl_Cache_Sync(){
        String url = "http://createTinyUrl";
        CreateTinyUrlCommand command = new CreateTinyUrlCommand();
        command.setUrl(url);
        command.setEnableCache(true);
        command.setEnableCacheSync(true);
        TinyUrl tinyUrl = this.commandApplicationService.createTinyUrl(command);
        Assert.assertNotNull(tinyUrl);
        Assert.assertNotNull(tinyUrl.getId());

        for (int i = 0; i< 100; i++) {
            Optional<TinyUrl> tinyUrlOptional = this.tinyUrlQueryApplicationService.findById(tinyUrl.getId());
            TinyUrl tinyUrlToCheck = tinyUrlOptional.orElse(null);
            Assert.assertNotNull(tinyUrlToCheck);
            Assert.assertEquals(tinyUrl.getId(), tinyUrlToCheck.getId());
            Assert.assertEquals(tinyUrl.getUrl(), tinyUrlToCheck.getUrl());
            Assert.assertEquals(TinyUrlType.COMMON, tinyUrlToCheck.getType());
            Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrlToCheck.getStatus());
            Assert.assertTrue(tinyUrlToCheck.canAccess());
        }
    }

}
