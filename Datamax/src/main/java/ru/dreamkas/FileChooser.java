package ru.dreamkas;

import java.io.File;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

public class FileChooser extends JFileChooser {
    static {
        UIManager.put("FileChooser.acceptAllFileFilterText", "Все файлы (*.*)");
        UIManager.put("FileChooser.lookInLabelText", "Переход");
        UIManager.put("FileChooser.openButtonText", "Открыть");
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.fileNameLabelText", "Имя файла");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файла");
        UIManager.put("FileChooser.openButtonToolTipText", "Открыть выбранный файл");
        UIManager.put("FileChooser.cancelButtonToolTipText","Отмена");
        UIManager.put("FileChooser.fileNameHeaderText","Имя файла");
        UIManager.put("FileChooser.upFolderToolTipText", "Вверх");
        UIManager.put("FileChooser.homeFolderToolTipText","Рабочий стол");
        UIManager.put("FileChooser.newFolderToolTipText","Создать папку");
        UIManager.put("FileChooser.listViewButtonToolTipText","Список");
        UIManager.put("FileChooser.newFolderButtonText","Создать папку");
        UIManager.put("FileChooser.renameFileButtonText", "Переименовать");
        UIManager.put("FileChooser.deleteFileButtonText", "Удалить файл");
        UIManager.put("FileChooser.filterLabelText", "Тип файла");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Подробно");
        UIManager.put("FileChooser.fileSizeHeaderText","Размер");
        UIManager.put("FileChooser.fileDateHeaderText", "Дата изменена");
    }

    private FileChooser(File file) {
        super(file);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public static FileChooser getOpenFileChooser(Path path, String title, FileFilter fileFilter) {
        FileChooser chooser = new FileChooser(path.toFile());
        chooser.setFileFilter(fileFilter);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setFileSystemView(FileSystemView.getFileSystemView());
        chooser.setDialogTitle(title);
        chooser.setApproveButtonToolTipText("Открыть файл");
        return chooser;
    }
}
