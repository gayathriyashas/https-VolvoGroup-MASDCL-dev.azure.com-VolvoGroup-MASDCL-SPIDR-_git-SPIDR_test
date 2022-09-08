package com.volvo.project.parameters;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExecutionParameters {
    private final String browser;
    private final boolean docker;
    private final boolean headless;
    private final boolean proxy;
    private final int resolutionWidth;
    private final int resolutionHeight;
}
