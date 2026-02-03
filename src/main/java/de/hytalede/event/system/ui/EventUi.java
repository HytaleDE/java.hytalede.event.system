package de.hytalede.event.system.ui;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nonnull;
import java.awt.*;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class EventUi extends InteractiveCustomUIPage<EventUi.Data> {
    private final String PanelMain = "#PanelMain";
    private final String LabelTest = "#LabelTest";
    private final String TextButtonTest = "#TextButtonTest";
    public String lang = "";

    public EventUi(@Nonnull PlayerRef playerRef, @Nonnull CustomPageLifetime lifetime) {
        super(playerRef, lifetime, Data.CODEC);
        this.lang = playerRef.getLanguage();
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder uiCommandBuilder, @Nonnull UIEventBuilder uiEventBuilder, @Nonnull Store<EntityStore> store) {
        uiCommandBuilder.append("EventUi.ui");
        uiCommandBuilder.set(LabelTest + ".TextSpans", Message.raw("Test Inhalt"));
        uiCommandBuilder.set(TextButtonTest + ".TextSpans", Message.raw("Button Text"));
        uiEventBuilder.addEventBinding(CustomUIEventBindingType.Activating, TextButtonTest, EventData.of("TextButtonTest", "true"), false);
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull Data data) {
        super.handleDataEvent(ref, store, data);
        super.sendUpdate();
    }

    public static class Data {
        public static final BuilderCodec<Data> CODEC = BuilderCodec.builder(Data.class, Data::new)
                .append(new KeyedCodec<>("TextButtonTest", Codec.STRING), (data, s) -> data.button = s, data -> data.button).add()
                .build();

        private String value;
        private String button;
    }

    private void writeNeutral(String label, String newText) {
        UICommandBuilder uiCommandBuilder = new UICommandBuilder();
        uiCommandBuilder.set(label, Message.raw(newText));
        super.sendUpdate(uiCommandBuilder, false);
    }

    private void writeError(String label, String newText) {
        UICommandBuilder uiCommandBuilder = new UICommandBuilder();
        uiCommandBuilder.set(label, Message.raw(newText).color(Color.RED));
        super.sendUpdate(uiCommandBuilder, false);
    }

    private void writeSuccess(String label, String newText) {
        UICommandBuilder uiCommandBuilder = new UICommandBuilder();
        uiCommandBuilder.set(label, Message.raw(newText).color(Color.GREEN));
        super.sendUpdate(uiCommandBuilder, false);
    }

    private void writeInfo(String label, String newText) {
        UICommandBuilder uiCommandBuilder = new UICommandBuilder();
        uiCommandBuilder.set(label, Message.raw(newText).color(Color.CYAN));
        super.sendUpdate(uiCommandBuilder, false);
    }
}