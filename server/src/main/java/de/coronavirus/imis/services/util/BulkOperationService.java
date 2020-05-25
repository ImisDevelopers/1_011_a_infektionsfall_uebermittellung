package de.coronavirus.imis.services.util;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Service
public class BulkOperationService {
  public interface Operation<Item, Error, ResultItem, Options, Details> {
    ItemStatus<ResultItem, Error, Details> process(Item item, Options options);
  }

  public <Item, Error, ResultItem, Options, Details>
    List<ItemStatus<ResultItem, Error, Details>> performBulkOperation(
      BulkRequest<Options, Item> request,
      Operation<Item, Error, ResultItem, Options, Details> itemHandler) {

    return request.getItems().stream()
      .map((var item) -> itemHandler.process(item, request.getOptions()))
      .collect(Collectors.toList());
  }


  @Data
  public static class BulkRequest<Options, Item> {
    private Options options;
    private List<Item> items;
  }

  @Accessors(chain = true)
  @Data
  public static class ItemStatus<Item, Error, Details> {
    private boolean success;
    private Error error;
    private Item result;
    private Details details;

    public ItemStatus<Item, Error, Details> setError(Error e, Details d) {
      this.error = e;
      this.details = d;
      this.success = false;
      return this;
    }

    public ItemStatus<Item, Error, Details> setError(Error e) {
      return this.setError(e, null);
    }

    public ItemStatus<Item, Error, Details> setResult(Item r, Details d) {
      this.result = r;
      this.details = d;
      this.success = true;
      return this;
    }

    public ItemStatus<Item, Error, Details> setResult(Item r) {
      return this.setResult(r, null);
    }
  }
}
