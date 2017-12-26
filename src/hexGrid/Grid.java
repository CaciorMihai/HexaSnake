package hexGrid;

import java.util.Iterator;

public class Grid implements Iterable<Hex> {
    private int radius;
    private Hex center;
    private Hex left;
    private int dimension;
    private Hex[][] map;
    public int counter = 0;

    private void fillNeighbours(Hex dummy) {
        if (dummy.getLeft() == null) {
            dummy.setLeft(new Hex());
            dummy.getLeft().setRight(dummy);
            dummy.getLeft().setX(dummy.getX() - 2 * this.getXoff());
            dummy.getLeft().setY(dummy.getY());
            dummy.getLeft().counter = counter++;
            dummy.getLeft().setI(dummy.getI() - 1);
            dummy.getLeft().setJ(dummy.getJ());
            map[dummy.getLeft().getI()][dummy.getLeft().getJ()] = dummy.getLeft();
        }
        if (dummy.getTopL() == null) {
            dummy.setTopL(new Hex());
            dummy.getTopL().setBotR(dummy);
            dummy.getTopL().setX(dummy.getX() - this.getXoff());
            dummy.getTopL().setY(dummy.getY() - this.getYoff());
            dummy.getTopL().counter = counter++;
            dummy.getTopL().setI(dummy.getI());
            dummy.getTopL().setJ(dummy.getJ() - 1);
            map[dummy.getTopL().getI()][dummy.getTopL().getJ()] = dummy.getTopL();
        }
        if (dummy.getTopR() == null) {
            dummy.setTopR(new Hex());
            dummy.getTopR().setBotL(dummy);
            dummy.getTopR().setX(dummy.getX() + this.getXoff());
            dummy.getTopR().setY(dummy.getY() - this.getYoff());
            dummy.getTopR().counter = counter++;
            dummy.getTopR().setI(dummy.getI() + 1);
            dummy.getTopR().setJ(dummy.getJ() - 1);
            map[dummy.getTopR().getI()][dummy.getTopR().getJ()] = dummy.getTopR();
        }
        if (dummy.getRight() == null) {
            dummy.setRight(new Hex());
            dummy.getRight().setLeft(dummy);
            dummy.getRight().setX(dummy.getX() + 2 * this.getXoff());
            dummy.getRight().setY(dummy.getY());
            dummy.getRight().counter = counter++;
            dummy.getRight().setI(dummy.getI() + 1);
            dummy.getRight().setJ(dummy.getJ());
            map[dummy.getRight().getI()][dummy.getRight().getJ()] = dummy.getRight();
        }
        if (dummy.getBotR() == null) {
            dummy.setBotR(new Hex());
            dummy.getBotR().setTopL(dummy);
            dummy.getBotR().setX(dummy.getX() + this.getXoff());
            dummy.getBotR().setY(dummy.getY() + this.getYoff());
            dummy.getBotR().counter = counter++;
            dummy.getBotR().setI(dummy.getI());
            dummy.getBotR().setJ(dummy.getJ() + 1);
            map[dummy.getBotR().getI()][dummy.getBotR().getJ()] = dummy.getBotR();
        }
        if (dummy.getBotL() == null) {
            dummy.setBotL(new Hex());
            dummy.getBotL().setTopR(dummy);
            dummy.getBotL().setX(dummy.getX() - this.getXoff());
            dummy.getBotL().setY(dummy.getY() + this.getYoff());
            dummy.getBotL().counter = counter++;
            dummy.getBotL().setI(dummy.getI() - 1);
            dummy.getBotL().setJ(dummy.getJ() + 1);
            map[dummy.getBotL().getI()][dummy.getBotL().getJ()] = dummy.getBotL();
        }
        dummy.getTopL().setRight(dummy.getTopR());
        dummy.getTopR().setLeft(dummy.getTopL());
        dummy.getTopR().setBotR(dummy.getRight());
        dummy.getRight().setTopL(dummy.getTopR());
        dummy.getBotR().setTopR(dummy.getRight());
        dummy.getRight().setBotL(dummy.getBotR());
        dummy.getBotR().setLeft(dummy.getBotL());
        dummy.getBotL().setRight(dummy.getBotR());
        dummy.getLeft().setBotR(dummy.getBotL());
        dummy.getBotL().setTopL(dummy.getLeft());
        dummy.getLeft().setTopR(dummy.getTopL());
        dummy.getTopL().setBotL(dummy.getLeft());
    }

    public Grid(final int r) {
        map = new Hex[2 * r + 2][2 * r + 2];
        Hex dummy;
        radius = r;
        dimension = 2*radius + 1;
        for (int i = 1; i <= radius; i++) {
            dimension += 2 * (2 * radius + 1 - i);
        }
        if (radius >= 0) {
            center = new Hex();
            center.counter = counter++;
            left = center;
            center.setI(radius + 1);
            center.setJ(radius + 1);
            map[center.getI()][center.getJ()] = center;
        }
        int i = 0;
        if (radius >= 1) {
            this.fillNeighbours(center);
            left = left.getLeft();
            i++;
        }
        while(i < radius) {
            dummy = left;
            while(dummy.getTopR() != null) {
                this.fillNeighbours(dummy);
                dummy = dummy.getTopR();
            }
            while(dummy.getRight() != null) {
                this.fillNeighbours(dummy);
                dummy = dummy.getRight();
            }
            while(dummy.getBotR() != null) {
                this.fillNeighbours(dummy);
                dummy = dummy.getBotR();
            }
            while(dummy.getBotL() != null) {
                this.fillNeighbours(dummy);
                dummy = dummy.getBotL();
            }
            while(dummy.getLeft() != null) {
                this.fillNeighbours(dummy);
                dummy = dummy.getLeft();
            }
            if(i < radius - 1) // Magic, do not touch!
            while(dummy.getTopL() != null) {
                this.fillNeighbours(dummy);
                dummy = dummy.getTopL();
            }
            left = left.getLeft();
            i++;
        }
    }

    public Hex getCenter() {
        return center;
    }
    public Hex[][] getMap() {
        return map;
    }

    public Iterator<Hex> iterator() {
        return new HexIterator();
    }
    private class HexIterator implements Iterator<Hex> {
        private Hex left;
        private Hex dummy;
        private int level;
        private short state;
        private int counter;
        private int number;
        private boolean bool;
        
        private HexIterator() {
            left = center;
            dummy = left;
            level = 0;
            state = 0;
            counter = 0;
            number = 0;
            bool = true;
        }
        public boolean hasNext() {
            return (number < dimension);
        }
        public Hex next() {
            if(level == 0) {
                level++;
                left = center.getLeft();
                dummy = center;
                bool = false;
            } else {
                /*
                 * state == 0  move topR
                 * state == 1  move right
                 * state == 2  move botR
                 * state == 3  move botL
                 * state == 4  move left
                 * state == 5  move topL
                 */
                if (!bool) {
                    dummy = left;
                    bool = true;
                }
                {
                    if (counter == level) {
                        counter = 0;
                        if (state < 5) {
                            state++;
                        } else {
                            state = 0;
                            level++;
                            left = left.getLeft();
                            dummy = left;
//                            number ++;
//                            return dummy;
                        }
                    }
                    switch (state) {
                        case 0:
                            dummy = dummy.getTopR();
                            break;
                        case 1:
                            dummy = dummy.getRight();
                            break;
                        case 2:
                            dummy = dummy.getBotR();
                            break;
                        case 3:
                            dummy = dummy.getBotL();
                            break;
                        case 4:
                            dummy = dummy.getLeft();
                            break;
                        case 5:
                            dummy = dummy.getTopL();
                            break;
                        default:
                            System.out.println("You're fucked.");
                            return null;
                    }
                    counter++;
                }
            }
            number ++;
            return dummy;
        }
        public void remove() { }
    }

    private float l = 1;
    private float h = (float) (l * Math.sqrt(3) / 2);
    private float getXoff() {
        return h;
    }
    private float getYoff() {
        return l * 1.5f;
    }
}
