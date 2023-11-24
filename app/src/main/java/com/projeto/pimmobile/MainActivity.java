package com.projeto.pimmobile;

        import androidx.annotation.RequiresApi;
        import androidx.appcompat.app.AppCompatActivity;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.view.KeyEvent;
        import android.view.View;
        import android.webkit.WebResourceRequest;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.ProgressBar;

        import java.net.URL;
        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    // Dar a capacidade do usuário retornar a área anterior com o botão
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // Construção do aplicativo em si
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        webView.loadUrl("https://gade-rose.vercel.app/");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        // Configurações de barra de navegação

        final ProgressBar progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Lista de URLs permitidas dentro do aplicativo
                List<String> allowedUrls = new ArrayList<>();
                allowedUrls.add("https://gade-rose.vercel.app/");
                allowedUrls.add("https://gade-rose.vercel.app/Pagina_de_menu.html");
                allowedUrls.add("https://gade-rose.vercel.app/esqueci_senha.html");
                allowedUrls.add("https://gade-rose.vercel.app/formulario/index.html");

                // Verificar se o URL está na lista de URLs permitidas
                boolean isAllowed = false;
                for (String allowedUrl : allowedUrls) {
                    if (url.startsWith(allowedUrl)) {
                        isAllowed = true;
                        break;
                    }
                }

                if (isAllowed) {
                    // Carregar a URL dentro do WebView
                    view.loadUrl(url);
                } else {
                    // Abrir o URL em um navegador externo
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}