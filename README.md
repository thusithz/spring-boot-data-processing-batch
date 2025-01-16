
# Data Processing Batch Application

This Spring Batch application processes server metrics data from Datadog logs and generates performance summaries. It includes scheduled jobs for processing server metrics and generating analytical reports.

## Features

1. **Server Metrics Processing**
   - Processes Datadog log files for server performance metrics
   - Monitors CPU and memory usage with configurable thresholds
   - Tracks request counts and response times
   - Generates performance summaries in CSV format

2. **Alert Monitoring**
   - Automatic alerts for high resource usage
   - CPU Usage threshold > 90%
   - Memory Usage threshold > 85%

3. **Batch Processing**
   - Chunk-based processing with configurable batch sizes
   - Efficient file reading with FlatFileItemReader
   - JSON parsing for Datadog log format
   - CSV output generation

## Project Structure

```
src/main/java/com/tk/batch/data_processing_batch/
├── config/
│   ├── BatchConfig.java         # Batch job configurations
│   ├── MetricProcessor.java     # Server metrics processor
│   └── DatadogLogReader.java    # Datadog log reader
├── controller/
│   └── BatchController.java     # REST endpoints
├── model/
│   ├── ServerMetric.java        # Server metric model
│   └── MetricSummary.java       # Processed summary model
└── DataProcessingBatchApplication.java
```

## Configuration

### Application Properties
```properties
spring.batch.jdbc.initialize-schema=always
spring.batch.job.enabled=false
spring.task.scheduling.pool.size=2
```

### Required Files
1. `datadog.log` - Place in `src/main/resources/`
2. `datadog_metrics.csv` - Place in `src/main/resources/`

## REST Endpoints

1. **Start Batch Processing**
   ```
   POST /start-batch
   ```

## Dependencies

- Spring Boot 3.1.0
- Spring Batch
- Spring Web
- H2 Database
- Lombok
- Jackson Databind

## Output

The application generates a `metric_summary.csv` file containing:
- Server ID
- Average CPU Usage
- Average Memory Usage
- Total Request Count
- Average Response Time

## Error Handling

- Failed jobs are automatically retried
- Job execution history maintained in H2 database
- Detailed logs generated for debugging
- Exception handling for JSON parsing errors

## Support

For issues and support:
1. Check application logs
2. Review job execution history
3. Verify input file formats (Datadog log format)
4. Monitor batch processing status
