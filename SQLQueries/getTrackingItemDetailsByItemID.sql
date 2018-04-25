select TrackItems.Description itemDes, TrackITems.CodeTrackItemCode,
intcompletedState.Description state, IntTrackingItemType.Description type
from TrackItems 
left outer join intcompletedState on Trackitems.CompletedState = intcompletedState.id
left outer join IntTrackingItemType on Trackitems.TrackType = IntTrackingItemType.id
where TrackItems.ID = '{argument}'

