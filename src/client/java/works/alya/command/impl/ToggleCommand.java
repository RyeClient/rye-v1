/*
 * Copyright (c) Alya Client 2024-2025.
 *
 * This file belongs to Alya Client,
 * an open-source Fabric injection client.
 * Rye GitHub: https://github.com/AlyaClient/alya-beta.git
 *
 * THIS PROJECT DOES NOT HAVE A WARRANTY.
 *
 * Alya (and subsequently, its files) are all licensed under the MIT License.
 * Alya should have come with a copy of the MIT License.
 * If it did not, you may obtain a copy here:
 * MIT License: https://opensource.org/license/mit
 *
 */

package works.alya.command.impl;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import works.alya.command.AbstractCommand;
import works.alya.module.Module;
import works.alya.module.ModuleRepository;
import works.alya.utilities.misc.ChatUtility;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;


public class ToggleCommand extends AbstractCommand {
    private final ModuleRepository moduleRepository;

    public ToggleCommand() {
        super("toggle");
        this.moduleRepository = ModuleRepository.getInstance();
    }

    @Override
    protected void build(LiteralArgumentBuilder<ServerCommandSource> builder) {
        builder
                .then(CommandManager.argument("name", StringArgumentType.word())
                        .suggests(this::suggestModules)
                        .executes(this::toggleModule))
                .executes(this::listModules);
    }

    private CompletableFuture<Suggestions> suggestModules(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        String input = builder.getRemaining().toLowerCase();
        for(Module module : moduleRepository.getModules()) {
            String moduleName = module.getName().toLowerCase();
            if(moduleName.startsWith(input)) {
                builder.suggest(module.getName());
            }
        }
        return builder.buildFuture();
    }


    private int toggleModule(CommandContext<ServerCommandSource> context) {
        String moduleName = StringArgumentType.getString(context, "name");
        Module module = moduleRepository.getModuleByName(moduleName);

        if(module == null) {
            ChatUtility.sendError("Module '" + moduleName + "' not found!");
            return 0;
        }

        module.toggle();
        ChatUtility.sendPrefixedMessage(
                "Module",
                module.getName() + " has been " + (module.isEnabled() ? "enabled" : "disabled"),
                Formatting.GOLD,
                module.isEnabled() ? Formatting.GREEN : Formatting.RED
        );
        return 1;
    }

    private int listModules(CommandContext<ServerCommandSource> context) {
        Collection<Module> modules = moduleRepository.getModules();

        ChatUtility.sendPrefixedMessage("Modules", "Available modules:", Formatting.GOLD, Formatting.WHITE);
        for(Module module : modules) {
            ChatUtility.sendMessage(
                    " - " + module.getName() + ": " +
                            (module.isEnabled() ? "§aEnabled" : "§cDisabled") +
                            " §7(" + module.getDescription() + ")"
            );
        }
        return 1;
    }
}
