package br.com.erudio.integrationtest.testcontainers

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.lifecycle.Startables
import java.util.stream.Stream

@ContextConfiguration(initializers = [AbstractIntegrationTest.Initializer::class])
open class AbstractIntegrationTest {

    internal class Initializer: ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            startContainers()

            val enviroment = applicationContext.environment
            val testcontainers = MapPropertySource(
                "testcontainers", createConnectionConfiguration()
            )
            enviroment.propertySources.addFirst(testcontainers)
        }

        companion object{
            private var mysql: MySQLContainer<*> = MySQLContainer("mysql:8.0.29")

            private fun startContainers() {
                Startables.deepStart(Stream.of(mysql)).join()
            }
            private fun createConnectionConfiguration(): MutableMap<String, Any> {
                return java.util.Map.of(
                    "spring.datasource.url", mysql.jdbcUrl,
                    "spring.datasource.username", mysql.username,
                    "spring.datasource.password", mysql.password,
                )
            }
        }

    }
}