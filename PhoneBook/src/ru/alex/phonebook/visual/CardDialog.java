package ru.alex.phonebook.visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.coobird.thumbnailator.Thumbnailator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import ru.alex.phonebook.classes.EmptyImage;
import ru.alex.phonebook.components.AbstractDataPanel;
import ru.alex.phonebook.components.AddressPanel;
import ru.alex.phonebook.components.EmailPanel;
import ru.alex.phonebook.components.ListLayout;
import ru.alex.phonebook.components.TelephonePanel;
import ezvcard.VCard;
import ezvcard.parameter.AddressType;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.ImageType;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.Address;
import ezvcard.property.Email;
import ezvcard.property.HasAltId;
import ezvcard.property.Photo;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;
import ezvcard.property.VCardProperty;

public class CardDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    public static final int CANCEL_OPTION = 1;
    public static final int APPROVE_OPTION = 0;
    public static final int ERROR_OPTION = -1;
    private static boolean showing;
    private static CardDialog dialog;
    private final JPanel contentPanel = new JPanel();
    private VCard card;
    private int result = ERROR_OPTION;

    private JTextField edtSurname;
    private JTextField edtName;
    private JTextField edtPrefix;
    private JTextField edtParentName;
    private JTextField edtSuffix;
    private JLabel lblPhoto;
    private JLabel lblPhotoDescription;
    private BufferedImage originalImage;
    private JPanel pnlTelephones;
    private JPanel pnlAddresses;
    private JPanel pnlEmail;

    private Action actOk = new AbstractAction("Сохранить") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                synchronizeCard();
                result = APPROVE_OPTION;
                setVisible(false);
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(CardDialog.this, e1.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    private Action actCancel = new AbstractAction("Отмена") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            result = CANCEL_OPTION;
            setVisible(false);
        }
    };

    private Action actOpenPhoto = new AbstractAction(null, new ImageIcon(CardDialog.class.getResource("/ru/alex/phonebook/img/open.png"))) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = getImageFileChooser(PhoneBookFrame.lastImageFolder, JFileChooser.OPEN_DIALOG);
            fc.setDialogType(JFileChooser.OPEN_DIALOG);
            if (fc.showDialog(CardDialog.this, null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                PhoneBookFrame.lastImageFolder = fc.getCurrentDirectory();
                try {
                    BufferedImage image = ImageIO.read(selectedFile);
                    if (image.getWidth() > 720 && image.getHeight() > 720)
                        originalImage = Thumbnailator.createThumbnail(image, 720, 720);
                    else
                        originalImage = image;
                    lblPhoto.setIcon(getPhoto());
                    lblPhotoDescription.setText(getPhotoDescription());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(CardDialog.this, e1.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };

    private Action actSavePhoto = new AbstractAction(null, new ImageIcon(CardDialog.class.getResource("/ru/alex/phonebook/img/save.png"))) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = getImageFileChooser(PhoneBookFrame.lastImageFolder, JFileChooser.SAVE_DIALOG);
            if (fc.showDialog(CardDialog.this, null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                PhoneBookFrame.lastImageFolder = fc.getCurrentDirectory();
                FileFilter choosedFilter = fc.getFileFilter();
                if (choosedFilter instanceof FileNameExtensionFilter) {
                    FileNameExtensionFilter filter = (FileNameExtensionFilter) choosedFilter;
                    if (StringUtils.isEmpty(FilenameUtils.getExtension(selectedFile.getName()))) {
                        selectedFile = new File(fc.getSelectedFile().getPath() + "." + Stream.of(filter.getExtensions()).findFirst().orElse("png"));
                    }
                }
                try {
                    ImageIO.write(originalImage, FilenameUtils.getExtension(selectedFile.getName()), selectedFile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(CardDialog.this, e1.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };

    private Action actClearPhoto = new AbstractAction(null, new ImageIcon(CardDialog.class.getResource("/ru/alex/phonebook/img/clear.png"))) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (JOptionPane.showConfirmDialog(CardDialog.this, "Удалить фотографию?", "Вопрос", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                originalImage = null;
                lblPhoto.setIcon(getPhoto());
                lblPhotoDescription.setText(getPhotoDescription());
            }
        }
    };

    private Action actAddTelephone = new AbstractAction("Добавить") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            Telephone telephone = new Telephone("");
            telephone.addType(TelephoneType.CELL);
            if (pnlTelephones.getComponentCount() == 0) {
                telephone.addType(TelephoneType.PREF);
            }
            TelephonePanel p = new TelephonePanel();
            p.setData(telephone);
            pnlTelephones.add(p);
            pnlTelephones.updateUI();
        }
    };

    private Action actAddEmail = new AbstractAction("Добавить") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            Email email = new Email("");
            email.addType(EmailType.HOME);
            if (pnlEmail.getComponentCount() == 0) {
                email.addType(EmailType.PREF);
            }
            EmailPanel p = new EmailPanel();
            p.setData(email);
            pnlEmail.add(p);
            pnlEmail.updateUI();
        }
    };

    private Action actAddAddress = new AbstractAction("Добавить") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            Address address = new Address();
            address.addType(AddressType.POSTAL);
            if (pnlAddresses.getComponentCount() == 0) {
                address.addType(AddressType.PREF);
            }
            AddressPanel p = new AddressPanel();
            p.setData(address);
            pnlAddresses.add(p);
            pnlAddresses.updateUI();
        }
    };

    public CardDialog(VCard card) {
        this.card = card;

        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/ru/alex/phonebook/img/main.png")));
        setTitle("Карточка абонента");

        initImage();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                actCancel.actionPerformed(new ActionEvent(event.getSource(), event.getID(), ""));
            }
        });
        setModal(true);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] {0, 0, 0};
        gbl_contentPanel.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 170, 200, 0};
        gbl_contentPanel.columnWeights = new double[] {0.0, 1.0, 0.0};
        gbl_contentPanel.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
        contentPanel.setLayout(gbl_contentPanel);
        {
            JLabel label = new JLabel("Префикс");
            GridBagConstraints gbc_label = new GridBagConstraints();
            gbc_label.anchor = GridBagConstraints.EAST;
            gbc_label.insets = new Insets(0, 0, 5, 5);
            gbc_label.gridx = 0;
            gbc_label.gridy = 0;
            contentPanel.add(label, gbc_label);
        }

        edtPrefix = new JTextField();
        GridBagConstraints gbc_edtPrefix = new GridBagConstraints();
        gbc_edtPrefix.insets = new Insets(0, 0, 5, 5);
        gbc_edtPrefix.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtPrefix.gridx = 1;
        gbc_edtPrefix.gridy = 0;
        contentPanel.add(edtPrefix, gbc_edtPrefix);
        edtPrefix.setColumns(10);

        {
            JPanel panel = new JPanel();
            panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.insets = new Insets(0, 0, 5, 0);
            gbc_panel.anchor = GridBagConstraints.NORTH;
            gbc_panel.gridheight = 7;
            gbc_panel.fill = GridBagConstraints.HORIZONTAL;
            gbc_panel.gridx = 2;
            gbc_panel.gridy = 0;
            contentPanel.add(panel, gbc_panel);
            panel.setLayout(new BorderLayout(0, 0));

            Icon photo = getPhoto();

            lblPhotoDescription = new JLabel(getPhotoDescription());
            lblPhotoDescription.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(lblPhotoDescription, BorderLayout.NORTH);

            lblPhoto = new JLabel(photo);
            lblPhoto.setMinimumSize(new Dimension(300, 300));
            panel.add(lblPhoto, BorderLayout.CENTER);
            lblPhoto.setPreferredSize(new Dimension(300, 300));

            JPanel panel_1 = new JPanel();
            FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
            flowLayout.setAlignment(FlowLayout.TRAILING);
            panel.add(panel_1, BorderLayout.SOUTH);

            JButton btnLoad = new JButton(actOpenPhoto);
            btnLoad.setMargin(new Insets(5, 5, 5, 5));
            panel_1.add(btnLoad);

            JButton btnSave = new JButton(actSavePhoto);
            btnSave.setMargin(new Insets(5, 5, 5, 5));
            panel_1.add(btnSave);

            JButton button = new JButton(actClearPhoto);
            button.setMargin(new Insets(5, 5, 5, 5));
            panel_1.add(button);

        }
        {
            JLabel label = new JLabel("Фамилия");
            GridBagConstraints gbc_label = new GridBagConstraints();
            gbc_label.insets = new Insets(0, 0, 5, 5);
            gbc_label.anchor = GridBagConstraints.EAST;
            gbc_label.gridx = 0;
            gbc_label.gridy = 1;
            contentPanel.add(label, gbc_label);
        }
        edtSurname = new JTextField();
        GridBagConstraints gbc_edtSurname = new GridBagConstraints();
        gbc_edtSurname.insets = new Insets(0, 0, 5, 5);
        gbc_edtSurname.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtSurname.gridx = 1;
        gbc_edtSurname.gridy = 1;
        contentPanel.add(edtSurname, gbc_edtSurname);
        edtSurname.setColumns(10);
        {
            JLabel label = new JLabel("Имя");
            GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
            gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
            gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel.gridx = 0;
            gbc_lblNewLabel.gridy = 2;
            contentPanel.add(label, gbc_lblNewLabel);
        }
        edtName = new JTextField();
        GridBagConstraints gbc_edtName = new GridBagConstraints();
        gbc_edtName.insets = new Insets(0, 0, 5, 5);
        gbc_edtName.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtName.gridx = 1;
        gbc_edtName.gridy = 2;
        contentPanel.add(edtName, gbc_edtName);
        edtName.setColumns(10);
        {
            JLabel label = new JLabel("Отчество");
            GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
            gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
            gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel_2.gridx = 0;
            gbc_lblNewLabel_2.gridy = 3;
            contentPanel.add(label, gbc_lblNewLabel_2);
        }
        edtParentName = new JTextField();
        GridBagConstraints gbc_edtParentName = new GridBagConstraints();
        gbc_edtParentName.insets = new Insets(0, 0, 5, 5);
        gbc_edtParentName.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtParentName.gridx = 1;
        gbc_edtParentName.gridy = 3;
        contentPanel.add(edtParentName, gbc_edtParentName);
        edtParentName.setColumns(10);
        {
            JLabel label = new JLabel("Суффикс");
            GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
            gbc_lblNewLabel_3.anchor = GridBagConstraints.NORTHEAST;
            gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel_3.gridx = 0;
            gbc_lblNewLabel_3.gridy = 4;
            contentPanel.add(label, gbc_lblNewLabel_3);
        }
        edtSuffix = new JTextField();
        GridBagConstraints gbc_edtSuffix = new GridBagConstraints();
        gbc_edtSuffix.anchor = GridBagConstraints.NORTH;
        gbc_edtSuffix.insets = new Insets(0, 0, 5, 5);
        gbc_edtSuffix.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtSuffix.gridx = 1;
        gbc_edtSuffix.gridy = 4;
        contentPanel.add(edtSuffix, gbc_edtSuffix);
        edtSuffix.setColumns(10);
        {
            JPanel pnlContent = new JPanel();
            pnlContent.setBorder(new TitledBorder(null, "Телефоны", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.gridheight = 3;
            gbc_panel.gridwidth = 2;
            gbc_panel.insets = new Insets(0, 0, 5, 5);
            gbc_panel.fill = GridBagConstraints.BOTH;
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 5;
            contentPanel.add(pnlContent, gbc_panel);
            pnlContent.setLayout(new BorderLayout(0, 0));

            JPanel pnlButton = new JPanel();
            pnlContent.add(pnlButton, BorderLayout.NORTH);
            FlowLayout flowLayout = (FlowLayout) pnlButton.getLayout();
            flowLayout.setAlignment(FlowLayout.LEADING);

            JButton btnNewButton = new JButton(actAddTelephone);
            pnlButton.add(btnNewButton);

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
            pnlContent.add(scrollPane, BorderLayout.CENTER);

            pnlTelephones = new JPanel(new ListLayout());
            scrollPane.setViewportView(pnlTelephones);

        }
        {
            JPanel pnlContent = new JPanel();
            pnlContent.setBorder(new TitledBorder(null, "E-Mail", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
            GridBagConstraints gbc_panel_1 = new GridBagConstraints();
            gbc_panel_1.insets = new Insets(0, 0, 5, 0);
            gbc_panel_1.fill = GridBagConstraints.BOTH;
            gbc_panel_1.gridx = 2;
            gbc_panel_1.gridy = 7;
            contentPanel.add(pnlContent, gbc_panel_1);
            pnlContent.setLayout(new BorderLayout(0, 0));

            JPanel pnlButton = new JPanel();
            pnlContent.add(pnlButton, BorderLayout.NORTH);
            FlowLayout flowLayout = (FlowLayout) pnlButton.getLayout();
            flowLayout.setAlignment(FlowLayout.LEADING);

            JButton button = new JButton(actAddEmail);
            pnlButton.add(button);

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
            pnlContent.add(scrollPane, BorderLayout.CENTER);

            pnlEmail = new JPanel(new ListLayout());
            scrollPane.setViewportView(pnlEmail);
            pnlEmail.setLayout(new ListLayout());

        }
        {
            JPanel pnlContent = new JPanel();
            pnlContent.setBorder(new TitledBorder(null, "Адрес", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
            GridBagConstraints gbc_panel_2 = new GridBagConstraints();
            gbc_panel_2.gridwidth = 3;
            gbc_panel_2.fill = GridBagConstraints.BOTH;
            gbc_panel_2.gridx = 0;
            gbc_panel_2.gridy = 8;
            contentPanel.add(pnlContent, gbc_panel_2);
            pnlContent.setLayout(new BorderLayout(0, 0));

            JPanel pnlButton = new JPanel();
            FlowLayout flowLayout = (FlowLayout) pnlButton.getLayout();
            flowLayout.setAlignment(FlowLayout.LEADING);
            pnlContent.add(pnlButton, BorderLayout.NORTH);

            JButton button = new JButton(actAddAddress);
            pnlButton.add(button);

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
            pnlContent.add(scrollPane, BorderLayout.CENTER);

            pnlAddresses = new JPanel(new ListLayout());
            scrollPane.setViewportView(pnlAddresses);
            pnlAddresses.setLayout(new ListLayout());
        }

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.TRAILING));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton(actOk);
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton(actCancel);
        buttonPane.add(cancelButton);

        initName();
        initField(card.getEmails().stream(), pnlEmail);
        initField(card.getTelephoneNumbers().stream(), pnlTelephones);
        initField(card.getAddresses().stream(), pnlAddresses);

        initGeo();
    }

    private void initName() {
        Optional.ofNullable(card.getStructuredName()).ifPresent(nameData -> {
            edtName.setText(nameData.getGiven());
            edtSurname.setText(nameData.getFamily());
            initNameFiled(edtPrefix, nameData.getPrefixes());
            initNameFiled(edtSuffix, nameData.getSuffixes());
            initNameFiled(edtParentName, nameData.getAdditional());
        });
    }

    private void initNameFiled(JTextField editor, List<String> values) {
        Optional.ofNullable(values).ifPresent(items -> {
            items.stream().findFirst().ifPresent(item -> {
                editor.setText(item);
            });
        });
    }

    @SuppressWarnings("unchecked")
    private <T extends VCardProperty> void initField(Stream<T> stream, Container parent) {
        Optional.ofNullable(stream).ifPresent(values -> {
            ButtonGroup group = new ButtonGroup();
            stream.forEach(value -> {
                try {
                    String s = AbstractDataPanel.class.getPackage().getName() + "." + value.getClass().getSimpleName() + "Panel";
                    AbstractDataPanel<T> panel = (AbstractDataPanel<T>) Class.forName(s).newInstance();
                    panel.setData(value);
                    parent.add(panel);
                    group.add(panel.getCheckBox());
                } catch (Exception e) {
                    e.printStackTrace();

                }
            });
        });
    }

    private void initGeo() {
        System.out.println("SortString: " + card.getVersion());
        Optional.ofNullable(card.getGeos()).ifPresent(geos -> {
            //            ButtonGroup group = new ButtonGroup();
            geos.stream().forEach(geo -> {
                System.out.println(geo.getType() + " " + geo.getLatitude() + " " + geo.getLongitude());
                //                AddressPanel addressesPanel = new AddressPanel();
                //                addressesPanel.setAddress(address);
                //                pnlAddresses.add(addressesPanel);
                //                group.add(addressesPanel.getCheckBox());
            });
        });
    }

    protected JFileChooser getImageFileChooser(File currentFolder, int dialogType) {
        UIManager.put("FileChooser.readOnly", Boolean.TRUE);
        if (currentFolder == null) {
            currentFolder = FileUtils.getFile(".");
        }
        JFileChooser fc = new JFileChooser(currentFolder);
        fc.setDialogType(dialogType);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        setImageFilters(fc, "png", "jpg", "gif", "bmp");
        return fc;
    }

    private void setImageFilters(JFileChooser fc, String... extension) {
        Stream.of(extension).forEach(ext -> {
            fc.addChoosableFileFilter(new FileNameExtensionFilter("Файлы изображений (*." + ext + ")", ext));
        });
        if (fc.getDialogType() == JFileChooser.SAVE_DIALOG) {
            fc.removeChoosableFileFilter(Stream.of(fc.getChoosableFileFilters()).findFirst().get());
        }
    }

    private void initImage() {
        originalImage = null;
        Optional.ofNullable(card.getPhotos()).ifPresent(photos -> {
            photos.stream().findFirst().ifPresent(photo -> {
                InputStream input = new ByteArrayInputStream(photo.getData());
                try {
                    originalImage = ImageIO.read(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private String getPhotoDescription() {
        return originalImage != null ? "Фото: " + originalImage.getWidth() + " x " + originalImage.getHeight() : "Фото отсутствует";
    }

    private Icon getPhoto() {
        Icon icon = new ImageIcon();
        if (originalImage != null) {
            if (originalImage.getWidth() > 300 || originalImage.getHeight() > 300) {
                ((ImageIcon) icon).setImage(Thumbnailator.createThumbnail(originalImage, 300, 300));
            } else {
                ((ImageIcon) icon).setImage(originalImage);
            }
        } else {
            icon = new EmptyImage(300, 300);
        }

        return icon;
    }

    protected void synchronizeCard() throws Exception {
        StructuredName nameData = card.getStructuredName();
        nameData.setFamily(edtSurname.getText());
        nameData.setGiven(edtName.getText());

        if (nameData.getPrefixes().size() > 0)
            nameData.getPrefixes().clear();
        if (!StringUtils.isEmpty(edtPrefix.getText()))
            nameData.addPrefix(edtPrefix.getText());

        if (nameData.getSuffixes().size() > 0)
            nameData.getSuffixes().clear();
        if (!StringUtils.isEmpty(edtSuffix.getText()))
            nameData.addSuffix(edtSuffix.getText());

        if (nameData.getAdditional().size() > 0)
            nameData.getAdditional().clear();
        if (!StringUtils.isEmpty(edtParentName.getText()))
            nameData.addAdditional(edtParentName.getText());

        syncField(Photo.class, null);
        syncField(Telephone.class, pnlTelephones);
        syncField(Email.class, pnlEmail);
        syncField(Address.class, pnlAddresses);
    }

    @SuppressWarnings("unchecked")
    private <T extends VCardProperty & HasAltId> void syncField(Class<T> clazz, Container container) throws Exception {
        card.setProperty(clazz, null);
        if (clazz.isAssignableFrom(Photo.class)) {
            ByteArrayOutputStream stream = null;
            try {
                card.setProperty(Photo.class, null);
                if (originalImage != null) {
                    stream = new ByteArrayOutputStream();
                    ImageIO.write(originalImage, "png", stream);
                    stream.flush();
                    byte[] imageBytes = stream.toByteArray();
                    Photo photo = new Photo(imageBytes, ImageType.PNG);
                    card.addPhoto(photo);
                }
            } finally {
                if (stream != null)
                    stream.close();
            }
        } else {     
            card.addPropertyAlt(clazz,
            Stream.of(container.getComponents())
                .filter(item -> item instanceof AbstractDataPanel)
                .map(item -> ((AbstractDataPanel<? extends T>) item).getData())
                .collect(Collectors.toList())
            );                
        }
    }
    
    public VCard getCard() {
        return card;
    }

    public void setCard(VCard card) {
        this.card = card;
    }

    public int showDialog() {
        setVisible(true);
        getContentPane().removeAll();
        dispose();
        return result;
    }

    @Override
    public void setVisible(boolean visible) {
        showing = visible;
        dialog = visible ? this : null;
        super.setVisible(visible);
    }

    public static boolean showing() {
        return showing;
    }

    public static void close() {
        if (dialog != null && dialog.isVisible()) {
            dialog.actCancel.actionPerformed(null);
        }
    }

}
