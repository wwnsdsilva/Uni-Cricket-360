package com.nsbm.uni_cricket_360.config;

import com.nsbm.uni_cricket_360.dto.BattingPerformanceDTO;
import com.nsbm.uni_cricket_360.dto.BowlingPerformanceDTO;
import com.nsbm.uni_cricket_360.dto.FieldingPerformanceDTO;
import com.nsbm.uni_cricket_360.dto.TrainingAttendanceDTO;
import com.nsbm.uni_cricket_360.entity.Attendance;
import com.nsbm.uni_cricket_360.entity.BattingPerformance;
import com.nsbm.uni_cricket_360.entity.BowlingPerformance;
import com.nsbm.uni_cricket_360.entity.FieldingPerformance;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();

        // player.id → player_id
        mapper.typeMap(BattingPerformance.class, BattingPerformanceDTO.class)
                .addMappings(m -> m.map(src -> src.getPlayer().getId(), BattingPerformanceDTO::setPlayer_id));

        mapper.typeMap(BowlingPerformance.class, BowlingPerformanceDTO.class)
                .addMappings(m -> m.map(src -> src.getPlayer().getId(), BowlingPerformanceDTO::setPlayer_id));

        mapper.typeMap(FieldingPerformance.class, FieldingPerformanceDTO.class)
                .addMappings(m -> m.map(src -> src.getPlayer().getId(), FieldingPerformanceDTO::setPlayer_id));

        return mapper;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Maps "/uploads/**" URLs to files in "uni-cricket-360"
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/home/shinyT480/IJSE/26_Freelance/Shewon/uni-cricket-360-uploads/");
    }
}
