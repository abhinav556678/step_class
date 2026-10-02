interface Playable {
    String play();
    String play(int fromSecond);
    String pause();
}

abstract class MediaFile {
    private static int counter = 1000;
    private final String fileId;

    public MediaFile() {
        counter++;
        this.fileId = "MF-" + counter;
    }

    public abstract String getFormatInfo();

    String getFileId() {
        return fileId;
    }
}

class AudioFile extends MediaFile implements Playable {
    private String title;

    public AudioFile(String title) {
        this.title = title;
    }

    @Override
    public String getFormatInfo() {
        return "Audio file, ID: " + getFileId();
    }

    @Override
    public String play() {
        return "Playing audio: " + title;
    }

    @Override
    public String play(int fromSecond) {
        int m = fromSecond / 60;
        int s = fromSecond % 60;
        return "Playing audio: " + title + " from " + m + ":" + String.format("%02d", s);
    }

    @Override
    public String pause() {
        return "Paused: " + title;
    }
}

class Podcast implements Playable {
    private String showName;
    private int episodeNumber;

    public Podcast(String showName, int episodeNumber) {
        this.showName = showName;
        this.episodeNumber = episodeNumber;
    }

    @Override
    public String play() {
        return "Streaming episode " + episodeNumber + " of " + showName;
    }

    @Override
    public String play(int fromSecond) {
        return play() + " from second " + fromSecond;
    }

    @Override
    public String pause() {
        return "Paused podcast";
    }
}

public class UniversalMediaLauncher {
    static void launchAll(Playable[] items) {
        for (Playable p : items) {
            System.out.println(p.play());
        }
    }

    public static void main(String[] args) {
        AudioFile a = new AudioFile("Morning Jazz");
        System.out.println(a.play());
        System.out.println(a.play(30));
        System.out.println(a.getFormatInfo());

        Podcast p = new Podcast("Tech Talk", 12);
        System.out.println(p.play());

        Playable ref = a; // upcasting
        System.out.println(ref.play());

        launchAll(new Playable[]{ ref, p });
    }
}
