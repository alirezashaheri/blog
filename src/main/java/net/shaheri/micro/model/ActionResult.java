package net.shaheri.micro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.shaheri.micro.model.enumeration.ActionStatus;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionResult implements Serializable {
    private ActionStatus status;
    private String id;
}
