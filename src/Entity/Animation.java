package Entity;

import java.awt.image.BufferedImage;

public class Animation {

	private BufferedImage[] _frames;
	private int _currentFrame;

	private long _startTime;
	private long _delay;
	private int _loop;
	private boolean _significantFrame;
	private boolean _playedOnce;

	private boolean _revloop;
	private int _dir = 1;

	public Animation() {
		init();
	}

	public void init() {
		_playedOnce = false;
		_significantFrame = false;
		_dir = 1;
		_currentFrame = 0;
	}

	public void setFrames(BufferedImage[] frames, int l) {
		this._frames = frames;
		_currentFrame = 0;
		_startTime = System.nanoTime();
		_playedOnce = false;
		_loop = l;

	}

	public void setFrames(BufferedImage[] frames) {
		this._frames = frames;
		_currentFrame = 0;
		_startTime = System.nanoTime();
		_playedOnce = false;
		_loop = -1;
	}

	public void setDelay(long d) {
		_delay = d;
	}

	public void setFrame(int i) {
		_currentFrame = i;
	}

	public void update() {
		if (_revloop)
			revLoopUpdate();
		else if (_loop == -1)
			basicUpdate();
		else
			loopUpdate();
	}

	public void basicUpdate() {
		if (_playedOnce) {
			return;
		}

		long elapsed = (System.nanoTime() - _startTime) / 1000000;
		if (elapsed > _delay) {
			_currentFrame++;
			_startTime = System.nanoTime();

		}

		if (_currentFrame >= _frames.length - 1) {
			_playedOnce = true;
		}
	}

	public void loopUpdate() {

		long elapsed = (System.nanoTime() - _startTime) / 1000000;
		if (elapsed > _delay) {
			_currentFrame++;
			_startTime = System.nanoTime();

		}

		if (_currentFrame == _frames.length) {
			_currentFrame = _loop;
			_playedOnce = true;
		}
	}

	public void revLoopUpdate() {		
		long elapsed = (System.nanoTime() - _startTime) / 1000000;
		if (elapsed > _delay) {		
				_currentFrame+=_dir;
			
			_startTime = System.nanoTime();

		}
		
		if(_playedOnce&&_currentFrame==0) {
			_playedOnce=false;
			_dir=-_dir;
		}
		
		if (_currentFrame == _frames.length-1) {
			_playedOnce = true;
			_dir=-_dir;
		}
		
		//not overflow
		if(_currentFrame<0)
			_currentFrame=0;
		if(_currentFrame>=_frames.length)
			_currentFrame = _frames.length-1;
		
	}

	public void set_revloop(boolean _revloop) {
		this._revloop = _revloop;
	}

	public void setSignificantFrame(boolean b) {
		_significantFrame = b;
	}

	public boolean getSignificantFrame() {
		return _significantFrame;
	}

	public int getFrame() {
		return _currentFrame;
	}

	public BufferedImage getImage() {
		return _frames[_currentFrame];
	}

	public void setImage(BufferedImage b) {
		_frames[_currentFrame] = b;
	}

	public boolean hasPlayedOnce() {
		return _playedOnce;
	}

}
