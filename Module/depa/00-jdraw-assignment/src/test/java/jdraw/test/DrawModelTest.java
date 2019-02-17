package jdraw.test;

import jdraw.framework.*;
import jdraw.std.StdDrawModel;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class DrawModelTest {

	static class TestFigure implements Figure {
		@Override public void setBounds(Point origin, Point corner) {}
		@Override public void draw(Graphics g) {}
		@Override public void move(int dx, int dy) {}
		@Override public boolean contains(int x, int y) { return false;	}
		@Override public java.awt.Rectangle getBounds() {return new java.awt.Rectangle();}
		@Override public List<FigureHandle> getHandles() { return null; }
		@Override public void addFigureListener(FigureListener listener) { }
		@Override public void removeFigureListener(FigureListener listener) { }
		@Override public Figure clone() { return null;}
	}

	private DrawModel m;
	private Figure f;
	private int cnt;
	private DrawModelEvent.Type type;

	protected DrawModel createDrawModel() {
		return new StdDrawModel();
	}

	@Before
	public void setUp() {
		m = createDrawModel();
		f = new TestFigure();
		cnt = 0;
		type = null;
	}

	@Test
	public void testAddFigure1() {
		m.addFigure(f);
		assertTrue("get back the Figure from the model", (m.getFigures().iterator().next()).equals(f));
	}

	@Test
	public void testAddFigure2() {
		Figure f1 = new TestFigure();
		Figure f2 = new TestFigure();
		Figure f3 = new TestFigure();
		m.addFigure(f1);
		m.addFigure(f2);
		m.addFigure(f3);
		String msg = "order of adding figures is not preserved";
		Iterator<Figure> it = m.getFigures().iterator();
		assertEquals(msg, f1, it.next());
		assertEquals(msg, f2, it.next());
		assertEquals(msg, f3, it.next());
	}

	@Test
	public void testAddFigure3() {
		m.addModelChangeListener(e -> type = e.getType());
		m.addFigure(f);
		assertEquals("addFigure should notify a FIGURE_ADDED event",
				DrawModelEvent.Type.FIGURE_ADDED, type);
	}

	@Test
	public void testAddFigure4() {
		m.addFigure(f);
		m.addFigure(f);
		Iterator<Figure> it = m.getFigures().iterator();
		it.next();
		assertFalse("figures in the model should be unique", it.hasNext());
	}

	@Test
	public void testAddFigure5() {
		m.addFigure(f);
		m.addModelChangeListener(e -> cnt++);
		m.addFigure(f);
		assertTrue("no notification if figure is already contained in model", cnt == 0);
	}

	@Test
	public void testAddFigure6() {
		Figure f = new TestFigure() {
			@Override
			public void addFigureListener(FigureListener listener) {
				cnt++;
			}
		};
		m.addFigure(f);
		assertTrue("model has to register a listener in the figure", cnt > 0);
	}

	@Test
	public void testRemoveFigure1() {
		m.addModelChangeListener(e -> cnt++);
		m.removeFigure(f);
		assertTrue(
				"no notification expected, figure was not contained in model", cnt == 0);
	}

	@Test
	public void testRemoveFigure2() {
		Figure f = new TestFigure() {
			@Override
			public void addFigureListener(FigureListener l) {
				cnt++;
			}

			@Override
			public void removeFigureListener(FigureListener l) {
				cnt--;
			}
		};
		m.addFigure(f);
		m.removeFigure(f);
		assertTrue("listeners registered by the model must be removed", cnt == 0);
	}

	@Test
	public void testRemoveFigure3() {
		List<FigureListener> listeners = new LinkedList<>();
		Figure f = new TestFigure() {
			@Override
			public void addFigureListener(FigureListener l) {
				listeners.add(l);
			}

			@Override
			public void removeFigureListener(FigureListener l) {
				listeners.add(l);
			}
		};
		m.addFigure(f);
		m.removeFigure(f);
		assertTrue("listeners registered by the model must be removed", listeners.size() == 2);
		assertTrue("the listener which has been registered must also be removed", listeners.get(0) == listeners.get(1));
	}

	@Test
	public void testRemoveFigure4() {
		List<FigureListener> listeners = new LinkedList<>();
		Figure f1 = new TestFigure() {
			@Override
			public void addFigureListener(FigureListener l) {
				listeners.add(l);
			}

			@Override
			public void removeFigureListener(FigureListener l) {
				listeners.add(l);
			}
		};
		Figure f2 = new TestFigure() {
			@Override
			public void addFigureListener(FigureListener l) {
				listeners.add(l);
			}

			@Override
			public void removeFigureListener(FigureListener l) {
				listeners.add(l);
			}
		};
		m.addFigure(f1);
		m.addFigure(f2);
		m.removeFigure(f1);
		m.removeFigure(f2);
		assertTrue("listeners registered by the model must be removed", listeners.size() == 4);
		assertTrue("the listener which has been registered must also be removed", listeners.get(0) == listeners.get(2));
		assertTrue("the listener which has been registered must also be removed", listeners.get(1) == listeners.get(3));
	}

	@Test
	public void testRemoveAllFigures1() {
		m.addFigure(f);
		m.addModelChangeListener(e -> type = e.getType());
		m.removeAllFigures();
		assertEquals("removeAllFigures should notify a DRAWING_CLEARED event",
				DrawModelEvent.Type.DRAWING_CLEARED, type);
	}

	@Test
	public void testRemoveAllFigures2() {
		class Fig extends TestFigure {
			@Override
			public void addFigureListener(FigureListener l) {
				cnt++;
			}

			@Override
			public void removeFigureListener(FigureListener l) {
				cnt--;
			}
		}
		;
		Figure f1 = new Fig();
		Figure f2 = new Fig();
		m.addFigure(f1);
		m.addFigure(f2);
		m.removeAllFigures();
		assertTrue("listeners registered by the model must be removed", cnt == 0);
	}

	@Test
	public void testSetFigureIndex1() {
		Figure f1 = new TestFigure();
		Figure f2 = new TestFigure();
		Figure f3 = new TestFigure();
		m.addFigure(f1);
		m.addFigure(f2);
		m.addFigure(f3);
		m.addModelChangeListener(e -> type = e.getType());
		m.setFigureIndex(f3, 0);
		Iterator<Figure> it = m.getFigures().iterator();
		assertEquals("f3 should be at position 0", f3, it.next());
		assertEquals("f1 should be at position 1", f1, it.next());
		assertEquals("f2 should be at position 2", f2, it.next());
		assertEquals("setFigureIndex should notify a DRAWING_CHANGED event",
				DrawModelEvent.Type.DRAWING_CHANGED, type);
	}

	@Test
	public void testSetFigureIndex2() {
		Figure f1 = new TestFigure();
		Figure f2 = new TestFigure();
		Figure f3 = new TestFigure();
		m.addFigure(f1);
		m.addFigure(f2);
		m.addFigure(f3);
		try {
			m.setFigureIndex(f, 1);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			// ok
		} catch (Exception e) {
			fail("IllegalArgumentException expected");
		}
	}

	@Test
	public void testSetFigureIndex3() {
		Figure f1 = new TestFigure();
		Figure f2 = new TestFigure();
		m.addFigure(f1);
		m.addFigure(f2);
		m.setFigureIndex(f2, 0);
		m.setFigureIndex(f2, 1);
		try {
			m.setFigureIndex(f2, 2);
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			// ok
		} catch (Exception e) {
			fail("IndexOutOfBoundsException expected");
		}

		assertTrue(
			"in case that an IndexOutOfBoundsException occurs, the figure must not be removed from the model",
			StreamSupport.stream(m.getFigures().spliterator(), false).anyMatch(f -> f == f2)
		);
	}

}
