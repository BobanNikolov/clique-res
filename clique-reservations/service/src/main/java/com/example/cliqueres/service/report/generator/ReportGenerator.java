package com.example.cliqueres.service.report.generator;

import com.example.cliqueres.domain.enums.FileType;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Map;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.RenderOption;

/**
 * UserToPoolReportConfigDto generator.
 */
public interface ReportGenerator {

  /**
   * Method to generate a report.
   *
   * @param report     IReportRunnable
   * @param reportMap  Map
   * @param birtEngine IReportEngine
   * @return byte[]
   * @throws EngineException exception
   */
  default byte[] generateReport(IReportRunnable report, Map<String, Collection<?>> reportMap,
      IReportEngine birtEngine)
      throws EngineException {
    IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
    reportMap.keySet()
        .forEach(k -> runAndRenderTask.getAppContext().put(k, reportMap.get(k).toArray()));
    RenderOption renderOption = new RenderOption();
    renderOption.setOutputFormat(getReportType().name().toLowerCase());
    return getBytes(runAndRenderTask, renderOption);
  }

  boolean canGenerate(FileType type);

  FileType getReportType();

  /**
   * Method to return bytes to an output stream.
   *
   * @param runAndRenderTask IRunAndRenderTask
   * @param renderOption     RenderOption
   * @return byte[]
   * @throws EngineException exception
   */
  default byte[] getBytes(IRunAndRenderTask runAndRenderTask, RenderOption renderOption)
      throws EngineException {
    runAndRenderTask.setRenderOption(renderOption);
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    renderOption.setOutputStream(stream);
    runAndRenderTask.run();
    runAndRenderTask.close();
    return stream.toByteArray();
  }
}

