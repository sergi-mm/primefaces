/**
 * Copyright 2009-2019 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.component.barchart;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.charts.ChartRenderer;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.util.WidgetBuilder;

public class BarChartRenderer extends ChartRenderer {

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        BarChart chart = (BarChart) component;
        String clientId = chart.getClientId(context);
        String style = chart.getStyle();
        String styleClass = chart.getStyleClass();

        encodeMarkup(context, clientId, style, styleClass);
        encodeScript(context, chart);
    }

    protected void encodeScript(FacesContext context, BarChart chart) throws IOException {
        String clientId = chart.getClientId(context);

        WidgetBuilder wb = getWidgetBuilder(context);
        wb.init("BarChart", chart.resolveWidgetVar(), clientId);

        encodeConfig(context, chart.getModel());
        encodeClientBehaviors(context, chart);

        wb.finish();
    }

    @Override
    protected void encodeOptions(FacesContext context, String type, Object options) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        if (options == null) {
            return;
        }

        BarChartOptions barOptions = (BarChartOptions) options;

        writer.write(",\"options\":{");

        writer.write("\"barPercentage\":" + barOptions.getBarPercentage());

        if (barOptions.getCategoryPercentage() != null) {
            writer.write(",\"categoryPercentage\":" + barOptions.getCategoryPercentage());
        }

        if (barOptions.getBarThickness() != null) {
            writer.write(",\"barThickness\":" + barOptions.getBarThickness());
        }

        if (barOptions.getMaxBarThickness() != null) {
            writer.write(",\"maxBarThickness\":" + barOptions.getMaxBarThickness());
        }

        if (barOptions.isOffsetGridLines()) {
            writer.write(",\"gridLines\":{");

            writer.write("\"offsetGridLines\":" + barOptions.isOffsetGridLines());

            writer.write("}");
        }

        encodeScales(context, type, barOptions.getScales(), true);
        encodeElements(context, barOptions.getElements(), true);
        encodeTitle(context, barOptions.getTitle(), true);
        encodeTooltip(context, barOptions.getTooltip(), true);
        encodeLegend(context, barOptions.getLegend(), true);

        writer.write("}");
    }
}
