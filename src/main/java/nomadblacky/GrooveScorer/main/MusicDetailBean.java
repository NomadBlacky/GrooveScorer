package nomadblacky.GrooveScorer.main;


public class MusicDetailBean {
	
	public static final String[] CSV_HEADER = new String[] {
		"id",
		"musicTitle",
		"artist",
		"skin",
		"totalPlayCount",
		"simpleScore",
		"simpleRate",
		"simpleChain",
		"simpleStatus",
		"simplePlayCount",
		"simpleNomissCount",
		"simpleFCCount",
		"normalScore",
		"normalRate",
		"normalChain",
		"normalStatus",
		"normalPlayCount",
		"normalNomissCount",
		"normalFCCount",
		"hardScore",
		"hardRate",
		"hardChain",
		"hardStatus",
		"hardPlayCount",
		"hardNomissCount",
		"hardFCCount",
		"extraScore",
		"extraRate",
		"extraChain",
		"extraStatus",
		"extraPlayCount",
		"extraNomissCount",
		"extraFCCount"
	};

	/**
	 * 楽曲ID
	 */
	private int id;

	/**
	 * 楽曲名
	 */
	private String musicTitle;

	/**
	 * アーティスト
	 */
	private String artist;

	/**
	 * 使用スキン
	 */
	private String skin;

	/**
	 * スコア
	 */
	private int simpleScore, normalScore, hardScore, extraScore;

	/**
	 * 評価
	 */
	private String simpleRate, normalRate, hardRate, extraRate;

	/**
	 * 最大チェイン
	 */
	private int simpleChain, normalChain, hardChain, extraChain;

	/**
	 * クリア状況
	 */
	private String simpleStatus, normalStatus, hardStatus, extraStatus;

	/**
	 * プレイ回数
	 */
	private int totalPlayCount, simplePlayCount, normalPlayCount, hardPlayCount, extraPlayCount;

	/**
	 * NoMiss回数
	 */
	private int simpleNomissCount, normalNomissCount, hardNomissCount, extraNomissCount;

	/**
	 * FullChain回数
	 */
	private int simpleFCCount, normalFCCount, hardFCCount, extraFCCount;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMusicTitle() {
		return musicTitle;
	}

	public void setMusicTitle(String musicTitle) {
		this.musicTitle = musicTitle;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public int getSimpleScore() {
		return simpleScore;
	}

	public void setSimpleScore(int simpleScore) {
		this.simpleScore = simpleScore;
	}

	public int getNormalScore() {
		return normalScore;
	}

	public void setNormalScore(int normalScore) {
		this.normalScore = normalScore;
	}

	public int getHardScore() {
		return hardScore;
	}

	public void setHardScore(int hardScore) {
		this.hardScore = hardScore;
	}

	public int getExtraScore() {
		return extraScore;
	}

	public void setExtraScore(int extraScore) {
		this.extraScore = extraScore;
	}

	public String getSimpleRate() {
		return simpleRate;
	}

	public void setSimpleRate(String simpleRate) {
		this.simpleRate = simpleRate;
	}

	public String getNormalRate() {
		return normalRate;
	}

	public void setNormalRate(String normalRate) {
		this.normalRate = normalRate;
	}

	public String getHardRate() {
		return hardRate;
	}

	public void setHardRate(String hardRate) {
		this.hardRate = hardRate;
	}

	public String getExtraRate() {
		return extraRate;
	}

	public void setExtraRate(String extraRate) {
		this.extraRate = extraRate;
	}

	public int getSimpleChain() {
		return simpleChain;
	}

	public void setSimpleChain(int simpleChain) {
		this.simpleChain = simpleChain;
	}

	public int getNormalChain() {
		return normalChain;
	}

	public void setNormalChain(int normalChain) {
		this.normalChain = normalChain;
	}

	public int getHardChain() {
		return hardChain;
	}

	public void setHardChain(int hardChain) {
		this.hardChain = hardChain;
	}

	public int getExtraChain() {
		return extraChain;
	}

	public void setExtraChain(int extraChain) {
		this.extraChain = extraChain;
	}

	public String getSimpleStatus() {
		return simpleStatus;
	}

	public void setSimpleStatus(String simpleStatus) {
		this.simpleStatus = simpleStatus;
	}

	public String getNormalStatus() {
		return normalStatus;
	}

	public void setNormalStatus(String normalStatus) {
		this.normalStatus = normalStatus;
	}

	public String getHardStatus() {
		return hardStatus;
	}

	public void setHardStatus(String hardStatus) {
		this.hardStatus = hardStatus;
	}

	public String getExtraStatus() {
		return extraStatus;
	}

	public void setExtraStatus(String extraStatus) {
		this.extraStatus = extraStatus;
	}

	public int getSimplePlayCount() {
		return simplePlayCount;
	}

	public void setSimplePlayCount(int simplePlayCount) {
		this.simplePlayCount = simplePlayCount;
	}

	public int getNormalPlayCount() {
		return normalPlayCount;
	}

	public void setNormalPlayCount(int normalPlayCount) {
		this.normalPlayCount = normalPlayCount;
	}

	public int getHardPlayCount() {
		return hardPlayCount;
	}

	public void setHardPlayCount(int hardPlayCount) {
		this.hardPlayCount = hardPlayCount;
	}

	public int getExtraPlayCount() {
		return extraPlayCount;
	}

	public void setExtraPlayCount(int extraPlayCount) {
		this.extraPlayCount = extraPlayCount;
	}

	public int getSimpleNomissCount() {
		return simpleNomissCount;
	}

	public void setSimpleNomissCount(int simpleNomissCount) {
		this.simpleNomissCount = simpleNomissCount;
	}

	public int getNormalNomissCount() {
		return normalNomissCount;
	}

	public void setNormalNomissCount(int normalNomissCount) {
		this.normalNomissCount = normalNomissCount;
	}

	public int getHardNomissCount() {
		return hardNomissCount;
	}

	public void setHardNomissCount(int hardNomissCount) {
		this.hardNomissCount = hardNomissCount;
	}

	public int getExtraNomissCount() {
		return extraNomissCount;
	}

	public void setExtraNomissCount(int extraNomissCount) {
		this.extraNomissCount = extraNomissCount;
	}

	public int getSimpleFCCount() {
		return simpleFCCount;
	}

	public void setSimpleFCCount(int simpleFCCount) {
		this.simpleFCCount = simpleFCCount;
	}

	public int getNormalFCCount() {
		return normalFCCount;
	}

	public void setNormalFCCount(int normalFCCount) {
		this.normalFCCount = normalFCCount;
	}

	public int getHardFCCount() {
		return hardFCCount;
	}

	public void setHardFCCount(int hardFCCount) {
		this.hardFCCount = hardFCCount;
	}

	public int getExtraFCCount() {
		return extraFCCount;
	}

	public void setExtraFCCount(int extraFCCount) {
		this.extraFCCount = extraFCCount;
	}

	public int getTotalPlayCount() {
		return totalPlayCount;
	}

	public void setTotalPlayCount(int totalPlay) {
		this.totalPlayCount = totalPlay;
	}

	@Override
	public String toString() {
		return "MusicDetailBean [id=" + id + ", musicTitle=" + musicTitle
				+ ", artist=" + artist + ", skin=" + skin + ", simpleScore="
				+ simpleScore + ", normalScore=" + normalScore + ", hardScore="
				+ hardScore + ", extraScore=" + extraScore + ", simpleRate="
				+ simpleRate + ", normalRate=" + normalRate + ", hardRate="
				+ hardRate + ", extraRate=" + extraRate + ", simpleChain="
				+ simpleChain + ", normalChain=" + normalChain + ", hardChain="
				+ hardChain + ", extraChain=" + extraChain + ", simpleStatus="
				+ simpleStatus + ", normalStatus=" + normalStatus
				+ ", hardStatus=" + hardStatus + ", extraStatus=" + extraStatus
				+ ", totalPlayCount=" + totalPlayCount + ", simplePlayCount="
				+ simplePlayCount + ", normalPlayCount=" + normalPlayCount
				+ ", hardPlayCount=" + hardPlayCount + ", extraPlayCount="
				+ extraPlayCount + ", simpleNomissCount=" + simpleNomissCount
				+ ", normalNomissCount=" + normalNomissCount
				+ ", hardNomissCount=" + hardNomissCount
				+ ", extraNomissCount=" + extraNomissCount + ", simpleFCCount="
				+ simpleFCCount + ", normalFCCount=" + normalFCCount
				+ ", hardFCCount=" + hardFCCount + ", extraFCCount="
				+ extraFCCount + "]";
	}

	
	public void setScore(DIFFICLITY difficlity, int score) {
		
		switch (difficlity) {
		case SIMPLE:
			setSimpleScore(score);
			break;
			
		case NORMAL:
			setNormalScore(score);
			break;
			
		case HARD:
			setHardScore(score);
			break;
			
		case EXTRA:
			setExtraScore(score);
			break;
		}
	}
	
	public void setRate(DIFFICLITY difficlity, String rate) {
		
		switch (difficlity) {
		case SIMPLE:
			setSimpleRate(rate);
			break;
			
		case NORMAL:
			setNormalRate(rate);
			break;
			
		case HARD:
			setHardRate(rate);
			break;
			
		case EXTRA:
			setExtraRate(rate);
			break;
		}
	}
	
	public void setChain(DIFFICLITY difficlity, int chain) {
		
		switch (difficlity) {
		case SIMPLE:
			setSimpleChain(chain);
			break;
			
		case NORMAL:
			setNormalChain(chain);
			break;
			
		case HARD:
			setHardChain(chain);
			break;
			
		case EXTRA:
			setExtraChain(chain);
			break;
		}
	}
	
	public void setStatus(DIFFICLITY difficlity, String status) {
		
		switch (difficlity) {
		case SIMPLE:
			setSimpleStatus(status);
			break;
			
		case NORMAL:
			setNormalStatus(status);
			break;
			
		case HARD:
			setHardStatus(status);
			break;
			
		case EXTRA:
			setExtraStatus(status);
			break;
		}
	}

	public void setPlayCount(DIFFICLITY difficlity, int count) {
		
		switch (difficlity) {
		case SIMPLE:
			setSimplePlayCount(count);
			break;
			
		case NORMAL:
			setNormalPlayCount(count);
			break;
			
		case HARD:
			setHardPlayCount(count);
			break;
			
		case EXTRA:
			setExtraPlayCount(count);
			break;
		}
	}
	
	public void setNomissCount(DIFFICLITY difficlity, int count) {
		
		switch (difficlity) {
		case SIMPLE:
			setSimpleNomissCount(count);
			break;
			
		case NORMAL:
			setNormalNomissCount(count);
			break;
			
		case HARD:
			setHardNomissCount(count);
			break;
			
		case EXTRA:
			setExtraNomissCount(count);
			break;
		}
	}
	
	public boolean isNomiss(DIFFICLITY difficlity) {
		
		switch (difficlity) {
		case SIMPLE:
			return getSimpleNomissCount() > 0 ? true : false;
			
		case NORMAL:
			return getNormalChain() > 0 ? true : false;
			
		case HARD:
			return getHardNomissCount() > 0 ? true : false;
			
		case EXTRA:
			return getExtraNomissCount() > 0 ? true : false;
			
		default:
			throw new RuntimeException();
		}
	}
	
	public void setFCCount(DIFFICLITY difficlity, int count) {
		
		switch (difficlity) {
		case SIMPLE:
			setSimpleFCCount(count);
			break;
			
		case NORMAL:
			setNormalFCCount(count);
			break;
			
		case HARD:
			setHardFCCount(count);
			break;
			
		case EXTRA:
			setExtraFCCount(count);
			break;
		}
	}
	
	public boolean isFullChain(DIFFICLITY difficlity) {
		
		switch (difficlity) {
		case SIMPLE:
			return getSimpleFCCount() > 0 ? true : false;
			
		case NORMAL:
			return getNormalFCCount() > 0 ? true : false;
			
		case HARD:
			return getHardFCCount() > 0 ? true : false;
			
		case EXTRA:
			return getExtraFCCount() > 0 ? true : false;
			
		default:
			throw new RuntimeException();
		}
	}

}
