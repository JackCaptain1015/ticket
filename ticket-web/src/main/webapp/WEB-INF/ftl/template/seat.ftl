<script id="seatTemplate" type="text/template">
    <div class="view-wrap" style="height: <%=allSeat.length * config.height%>px; width: <%=width%>px; display: none;">
        <div class="view-box">
            <% var sold = false;%>
            <%for(var i in allSeat) {%>
            <%var row = allSeat[i];%>
                <%if(!row) continue;%>
                <%for(var j in row) {%>
                    <%var t = row[j];%>
                    <%if(pickedSeat[i] && pickedSeat[i].indexOf(t) > -1) {%>
                        <% sold = true;%>
                     <%}%>
                    <div class="unregular-seat seat-cell <%if(sold) {%> seat-sold <%}%> js-pick-seat" data-name="<%=i%>排<%=t%>座" data-seatid="<%=i%>,<%=t%>"
                        data-flag="0" style="margin-left:<%=t * config.width%>px;margin-top: <%=i * config.height%>px;width:<%=config.width%>px;height:<%=config.height%>px;">
                    </div>
                    <% sold = false;%>
                 <%}%>
            <%}%>
        </div>
    </div>
</script>

<script>
    function convertSeatData(list) {
        var ret = [], t,
            reg = /\((\d+)[,|-](\d+)\)/;

        if(typeof list == 'string') {
            list = list.split('-');
        }

        for(var i in list) {
            t = list[i].match(reg);

            if(!ret[ t[1] ]) {
                ret[ t[1] ] = [];
            }
            ret[ t[1] ].push(t[2]);
        }
        return ret;
    }

    function getSeatViewWidth(width, list) {
        var max = 0;

        for(var i in list) {
            for(var j in list[i]) {
                max = max < +list[i][j] ? list[i][j] : max;
            }
        }
        return max * width;
    }
</script>