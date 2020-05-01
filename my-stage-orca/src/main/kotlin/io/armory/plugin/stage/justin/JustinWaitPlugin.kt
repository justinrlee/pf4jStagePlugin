package io.armory.plugin.stage.justin

import com.netflix.spinnaker.kork.plugins.api.ExtensionConfiguration
import org.slf4j.LoggerFactory
import org.pf4j.Extension
import org.pf4j.Plugin
import org.pf4j.PluginWrapper
import com.netflix.spinnaker.orca.api.SimpleStageStatus
import com.netflix.spinnaker.orca.api.SimpleStageOutput
import java.util.concurrent.TimeUnit
import com.netflix.spinnaker.orca.api.SimpleStageInput
import com.netflix.spinnaker.orca.api.SimpleStage
import java.util.*


class JustinWaitPlugin(wrapper: PluginWrapper) : Plugin(wrapper) {
    private val logger = LoggerFactory.getLogger(JustinWaitPlugin::class.java)

    override fun start() {
        logger.info("Plugin starting, yay")
    }

    override fun stop() {
        logger.info("Plugins stopping, oh no.")
    }
}

/**
 * Example stage that implements the Orca API Stage interface. By implementing Stage,
 * your stage is available for use in Spinnaker.
 */
@Extension
class JustinWaitStage(val configuration: JustinWaitConfig) : SimpleStage<JustinWaitInput> {

    private val log = LoggerFactory.getLogger(SimpleStage::class.java)

    /**
     * This sets the name of the stage
     * @return the name of the stage
     */
    override fun getName(): String {
        return "justinWait"
    }

    /**
     * This is what gets ran when the stage is executed. It takes in an object that you create. That
     * object contains fields that one wishes to pull out of the pipeline context. This gives us a
     * strongly typed object that you have full control over. The function returns a SimpleStageOutput object.
     * The SimpleStageOutput class contains the status of the stage and any stage outputs that should be
     * put back into the pipeline context.
     * @param stageInput<RandomWaitInput>
     * @return the status of the stage and any context that should be passed to the pipeline context
    </RandomWaitInput> */
    override fun execute(stageInput: SimpleStageInput<JustinWaitInput>): SimpleStageOutput<*, *> {
        val rand = Random()
        val maxWaitTime = stageInput.value.justinWaitTime
        val timeToWait = rand.nextInt(maxWaitTime)

        try {
            TimeUnit.SECONDS.sleep(timeToWait.toLong())
        } catch (e: Exception) {
            log.error("{}", e)
        }

        val stageOutput = SimpleStageOutput<Output, Context>()
        val output = Output(timeToWait)
        val context = Context(maxWaitTime)

        stageOutput.setOutput(output)
        stageOutput.setContext(context)
        stageOutput.setStatus(SimpleStageStatus.SUCCEEDED)

        return stageOutput
    }
}
