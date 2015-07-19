<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<div style="width:400px; font-size: 12px; line-height: 1em;">
    <table border="0" width="100%" cellpadding="0" cellspacing="0" id="product-table">
        <tr>
            <td><strong>Max Speed</strong></td>
            <td>${maxSpeed} km/h</td>
        </tr>
        <tr class="alternate-row">
            <td><strong>Trip Length</strong></td>
            <td>${tripLength} km</td>
        </tr>
        <tr>
            <td><strong>Trip time</strong></td>
            <td>${tripTime}</td>
        </tr>
        <tr class="alternate-row">
            <td><strong>Average Speed</strong></td>
            <td>${avgSpeed} km/h</td>
        </tr>
    </table>
</div>
