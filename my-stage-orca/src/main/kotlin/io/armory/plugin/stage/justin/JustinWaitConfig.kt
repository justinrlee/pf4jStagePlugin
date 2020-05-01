package io.armory.plugin.stage.justin

import com.netflix.spinnaker.kork.plugins.api.ExtensionConfiguration

@ExtensionConfiguration("armory.justinStage")
data class JustinWaitConfig(var justinDefaultMWT: Int)
