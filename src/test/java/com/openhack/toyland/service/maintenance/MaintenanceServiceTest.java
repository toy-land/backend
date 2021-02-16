package com.openhack.toyland.service.maintenance;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest
class MaintenanceServiceTest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    void apiParse() throws JsonProcessingException {
        String body = "{\n"
            + "   \"id\":283999126,\n"
            + "   \"node_id\":\"MDEwOlJlcG9zaXRvcnkyODM5OTkxMjY=\",\n"
            + "   \"name\":\"Android_2_Backend\",\n"
            + "   \"full_name\":\"Yapp-17th/Android_2_Backend\",\n"
            + "   \"private\":false,\n"
            + "   \"owner\":{\n"
            + "      \"login\":\"Yapp-17th\",\n"
            + "      \"id\":69037013,\n"
            + "      \"node_id\":\"MDEyOk9yZ2FuaXphdGlvbjY5MDM3MDEz\",\n"
            + "      \"avatar_url\":\"https://avatars.githubusercontent.com/u/69037013?v=4\",\n"
            + "      \"gravatar_id\":\"\",\n"
            + "      \"url\":\"https://api.github.com/users/Yapp-17th\",\n"
            + "      \"html_url\":\"https://github.com/Yapp-17th\",\n"
            + "      \"followers_url\":\"https://api.github.com/users/Yapp-17th/followers\",\n"
            + "      \"following_url\":\"https://api.github.com/users/Yapp-17th/following{/other_user}\",\n"
            + "      \"gists_url\":\"https://api.github.com/users/Yapp-17th/gists{/gist_id}\",\n"
            + "      \"starred_url\":\"https://api.github.com/users/Yapp-17th/starred{/owner}{/repo}\",\n"
            + "      \"subscriptions_url\":\"https://api.github.com/users/Yapp-17th/subscriptions\",\n"
            + "      \"organizations_url\":\"https://api.github.com/users/Yapp-17th/orgs\",\n"
            + "      \"repos_url\":\"https://api.github.com/users/Yapp-17th/repos\",\n"
            + "      \"events_url\":\"https://api.github.com/users/Yapp-17th/events{/privacy}\",\n"
            + "      \"received_events_url\":\"https://api.github.com/users/Yapp-17th/received_events\",\n"
            + "      \"type\":\"Organization\",\n"
            + "      \"site_admin\":false\n"
            + "   },\n"
            + "   \"html_url\":\"https://github.com/Yapp-17th/Android_2_Backend\",\n"
            + "   \"description\":\"Backend Repository for Android Team 2\",\n"
            + "   \"fork\":false,\n"
            + "   \"url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend\",\n"
            + "   \"forks_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/forks\",\n"
            + "   \"keys_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/keys{/key_id}\",\n"
            + "   \"collaborators_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/collaborators{/collaborator}\",\n"
            + "   \"teams_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/teams\",\n"
            + "   \"hooks_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/hooks\",\n"
            + "   \"issue_events_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/issues/events{/number}\",\n"
            + "   \"events_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/events\",\n"
            + "   \"assignees_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/assignees{/user}\",\n"
            + "   \"branches_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/branches{/branch}\",\n"
            + "   \"tags_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/tags\",\n"
            + "   \"blobs_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/git/blobs{/sha}\",\n"
            + "   \"git_tags_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/git/tags{/sha}\",\n"
            + "   \"git_refs_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/git/refs{/sha}\",\n"
            + "   \"trees_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/git/trees{/sha}\",\n"
            + "   \"statuses_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/statuses/{sha}\",\n"
            + "   \"languages_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/languages\",\n"
            + "   \"stargazers_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/stargazers\",\n"
            + "   \"contributors_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/contributors\",\n"
            + "   \"subscribers_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/subscribers\",\n"
            + "   \"subscription_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/subscription\",\n"
            + "   \"commits_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/commits{/sha}\",\n"
            + "   \"git_commits_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/git/commits{/sha}\",\n"
            + "   \"comments_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/comments{/number}\",\n"
            + "   \"issue_comment_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/issues/comments{/number}\",\n"
            + "   \"contents_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/contents/{+path}\",\n"
            + "   \"compare_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/compare/{base}...{head}\",\n"
            + "   \"merges_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/merges\",\n"
            + "   \"archive_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/{archive_format}{/ref}\",\n"
            + "   \"downloads_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/downloads\",\n"
            + "   \"issues_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/issues{/number}\",\n"
            + "   \"pulls_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/pulls{/number}\",\n"
            + "   \"milestones_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/milestones{/number}\",\n"
            + "   \"notifications_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/notifications{?since,all,participating}\",\n"
            + "   \"labels_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/labels{/name}\",\n"
            + "   \"releases_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/releases{/id}\",\n"
            + "   \"deployments_url\":\"https://api.github.com/repos/Yapp-17th/Android_2_Backend/deployments\",\n"
            + "   \"created_at\":\"2020-07-31T09:45:41Z\",\n"
            + "   \"updated_at\":\"2021-01-18T16:36:25Z\",\n"
            + "   \"pushed_at\":\"2021-01-18T16:36:27Z\",\n"
            + "   \"git_url\":\"git://github.com/Yapp-17th/Android_2_Backend.git\",\n"
            + "   \"ssh_url\":\"git@github.com:Yapp-17th/Android_2_Backend.git\",\n"
            + "   \"clone_url\":\"https://github.com/Yapp-17th/Android_2_Backend.git\",\n"
            + "   \"svn_url\":\"https://github.com/Yapp-17th/Android_2_Backend\",\n"
            + "   \"homepage\":\"https://github.com/Yapp-17th/Android_2_Backend/wiki\",\n"
            + "   \"size\":812,\n"
            + "   \"stargazers_count\":2,\n"
            + "   \"watchers_count\":2,\n"
            + "   \"language\":\"Java\",\n"
            + "   \"has_issues\":true,\n"
            + "   \"has_projects\":true,\n"
            + "   \"has_downloads\":true,\n"
            + "   \"has_wiki\":true,\n"
            + "   \"has_pages\":false,\n"
            + "   \"forks_count\":2,\n"
            + "   \"mirror_url\":null,\n"
            + "   \"archived\":false,\n"
            + "   \"disabled\":false,\n"
            + "   \"open_issues_count\":8,\n"
            + "   \"license\":null,\n"
            + "   \"forks\":2,\n"
            + "   \"open_issues\":8,\n"
            + "   \"watchers\":2,\n"
            + "   \"default_branch\":\"develop\",\n"
            + "   \"temp_clone_token\":null,\n"
            + "   \"organization\":{\n"
            + "      \"login\":\"Yapp-17th\",\n"
            + "      \"id\":69037013,\n"
            + "      \"node_id\":\"MDEyOk9yZ2FuaXphdGlvbjY5MDM3MDEz\",\n"
            + "      \"avatar_url\":\"https://avatars.githubusercontent.com/u/69037013?v=4\",\n"
            + "      \"gravatar_id\":\"\",\n"
            + "      \"url\":\"https://api.github.com/users/Yapp-17th\",\n"
            + "      \"html_url\":\"https://github.com/Yapp-17th\",\n"
            + "      \"followers_url\":\"https://api.github.com/users/Yapp-17th/followers\",\n"
            + "      \"following_url\":\"https://api.github.com/users/Yapp-17th/following{/other_user}\",\n"
            + "      \"gists_url\":\"https://api.github.com/users/Yapp-17th/gists{/gist_id}\",\n"
            + "      \"starred_url\":\"https://api.github.com/users/Yapp-17th/starred{/owner}{/repo}\",\n"
            + "      \"subscriptions_url\":\"https://api.github.com/users/Yapp-17th/subscriptions\",\n"
            + "      \"organizations_url\":\"https://api.github.com/users/Yapp-17th/orgs\",\n"
            + "      \"repos_url\":\"https://api.github.com/users/Yapp-17th/repos\",\n"
            + "      \"events_url\":\"https://api.github.com/users/Yapp-17th/events{/privacy}\",\n"
            + "      \"received_events_url\":\"https://api.github.com/users/Yapp-17th/received_events\",\n"
            + "      \"type\":\"Organization\",\n"
            + "      \"site_admin\":false\n"
            + "   },\n"
            + "   \"network_count\":2,\n"
            + "   \"subscribers_count\":2\n"
            + "}";
        ObjectNode jsonNodes = new ObjectMapper().readValue(body, ObjectNode.class);

        JsonNode pushed_at = jsonNodes.get("pushed_at");

        assertThat(pushed_at.asText()).isEqualTo("2021-01-18T16:36:27Z");
    }

    @Test
    void fetchFromGithubApiAndParse() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "https://api.github.com/repos/Yapp-17th/Android_2_Backend", String.class);
        String body = response.getBody();
        ObjectNode jsonNodes = new ObjectMapper().readValue(body, ObjectNode.class);

        JsonNode pushed_at = jsonNodes.get("pushed_at");
        assertThat(pushed_at.asText()).isNotBlank();
    }
}