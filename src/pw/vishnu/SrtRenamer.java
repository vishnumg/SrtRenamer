package pw.vishnu;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class SrtRenamer {

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SrtRenamer window = new SrtRenamer();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();

		DirectoryDialog dialog = new DirectoryDialog(shell);

		ProgressBar progressBar = new ProgressBar(shell, SWT.NONE);
		progressBar.setBounds(74, 70, 150, 20);
		String dirPath = dialog.open();

		final File folder = new File(dirPath);

		File[] subfiles = folder.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getName().endsWith(".srt");
			}
		});
		File[] vidfiles = folder.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getName().endsWith(".avi");
			}
		});

		Arrays.sort(vidfiles);
		Arrays.sort(subfiles);
		int loopcount = (vidfiles.length < subfiles.length ? vidfiles.length
				: subfiles.length);
		progressBar.setMaximum(loopcount - 1);
		progressBar.setMinimum(0);

		for (int i = 0; i < loopcount; i++) {
			File subfile = subfiles[i];
			File vidfile = vidfiles[i];
			subfile.renameTo(new File(vidfile.getAbsolutePath().replaceAll(
					".avi", ".srt")));
			progressBar.setSelection(i);

		}
		shell.dispose();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");

	}

	public void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				System.out.println(fileEntry.getName());
			}
		}
	}
}
