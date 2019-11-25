import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

@State(name = "TailwindConfiguration", storages = { @Storage("TailwindConfiguration.xml")})
public class TailwindConfiguration implements ApplicationComponent, PersistentStateComponent<TailwindConfiguration.State> {
    public static class State {
        @Attribute("order")
        public Collection<String> order;

        public State() {
            System.out.println("Getting default state");
            order = (new TailwindUtility()).classOrder;
        }
    }
    public State tailwindState;

    @NotNull
    @Override
    public String getComponentName() {
        return getClass().getSimpleName();
    }
    @Override
    public void disposeComponent() {}
    @Override
    public void initComponent() {
        System.out.println("initComponent()");
        this.tailwindState = new TailwindConfiguration.State();
    }

    @Override
    public TailwindConfiguration.State getState() {
        System.out.println("getState() returning");
        return tailwindState;
    }

    @Override
    public void loadState(TailwindConfiguration.State state) {
        System.out.println("loadState()");
        tailwindState = state;
    }
}
