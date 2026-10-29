package entidades;

public abstract class Entidad {
    protected int id;
    public int getId() { return id; }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Entidad e))
            return false;
        return e.getId() == this.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
