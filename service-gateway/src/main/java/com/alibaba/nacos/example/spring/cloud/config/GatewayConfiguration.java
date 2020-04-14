package com.alibaba.nacos.example.spring.cloud.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author Eric Zhao
 */
@Configuration
public class GatewayConfiguration {

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @PostConstruct
    public void doInit() {
        initCustomizedApis();
        initGatewayRules();
        initDegradeRule();
    }

    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition api3 = new ApiDefinition("client_consumer1")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern("/sentinel/qps")
                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                }});
        definitions.add(api3);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        //此处的 resource 要等于 网关配置规则的id
        rules.add(new GatewayFlowRule("client_consumer1_sentinel")
                .setCount(2)
                .setIntervalSec(1)
        );

        rules.add(new GatewayFlowRule("client_consumer1_sentinel_thread")
                        .setGrade(RuleConstant.FLOW_GRADE_THREAD)
                        .setCount(5)
                        .setIntervalSec(1)
                        .setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER)
        );
        GatewayRuleManager.loadRules(rules);
    }

    /**
     * https://blog.csdn.net/Ramboll/article/details/84942779
     */
    private void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        // 设置资源名，资源名是限流规则的作用对象
        rule.setResource("client_consumer1_sentinel");
        // 设置平均响应时间阈值为10ms
        rule.setCount(10);
        // 设置降级模式，根据平均响应时间降级
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        // 设置降级的时间，以s为单位
        rule.setTimeWindow(60);
        rules.add(rule);
        //还可以根据异常比率进行降级
        DegradeRuleManager.loadRules(rules);
    }
}