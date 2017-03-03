package swt6.osgi.util;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class JavaFxUtils {
  
  private static JFXPanel jFxPanel;
  
  public static void initJavaFx() {
    if (jFxPanel == null) {
      jFxPanel = new JFXPanel(); // initialize JavaFX toolkit
      Platform.setImplicitExit(false);
    }
  }

  public static void exitJavaFx() {
    Platform.runLater(() -> Platform.exit());
  }

  public static void runAndWait(Runnable runnable)
      throws InterruptedException, ExecutionException {
    if (Platform.isFxApplicationThread()) {
      try {
        runnable.run();
      }
      catch (Exception e) {
        throw new ExecutionException(e);
      }
    }
    else {
      FutureTask<Object> futureTask = new FutureTask<>(runnable, null);
      Platform.runLater(futureTask);
      futureTask.get();
    }
  }

  public static void runLater(Runnable runnable) {
    Platform.runLater(runnable);
  }
}
