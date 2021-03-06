package mx.edu.ittepic.dadm_minigameu3_anacarolina_zulmaisabel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Random;

public class Pikarun_act extends AppCompatActivity {

    CountDownTimer principal,puntuacionCont,global;
    int puntuacionGlobal,resulusionx,resulusiony,velocidadrelog=2000;
    Vibrator sistemavibrador;
    Musica reproductor,pikasonido,pikadeath;

    // Sprite boton1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PikaRUN(this));

        //boton1= new Sprite(BitmapFactory.decodeResource(getResources(),R.drawable.botonsur),(resulusionx/2)-(resulusionx/9),(resulusiony/2)-50,resulusiony/2);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            reproductor.detener();
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onResume() {
        super.onResume();
        reproductor.reproducir();
    }
    @Override
    protected void onPause() {
        super.onPause();
        reproductor.detener();
    }
    public class PikaRUN extends View {
        boolean JUEGO=true,vibrar=true;
        SpriteAnim pikarunsp;
        Sprite [] capas;
        Sprite  puntos,gameover;
        int img[]={R.drawable.pikarun1,
                R.drawable.pikarun2,
                R.drawable.pikarun3,
                R.drawable.pikarun4,
                R.drawable.pikarun5,
                R.drawable.pikarun6};
        int  layers[]={R.drawable.layerr1,
                R.drawable.layerr1,
                R.drawable.layerr0,
                R.drawable.layerr0};

        Bitmap [] imge;
        int  pixelArt[]={R.drawable.roca};
        Objetos [] assets;
        CountDownTimer ObjetosEntregar;
        int posrocas=0,velocidad=-15;
        public PikaRUN (Context context) {
            super(context);
            Resolucion();
            sistemavibrador = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            imge= new Bitmap[img.length];
            capas= new Sprite[4];
            for(int i=0; i<capas.length ;i++)
            {
                capas[i] = new Sprite(BitmapFactory.decodeResource(getResources(),layers[i]),0,0,(float)(resulusiony*1.7));
            }
            capas[1].x=capas[0].getTamano();
            capas[3].x=resulusionx+15;
            for(int i=0;i<img.length;i++)
            {
                imge[i]=BitmapFactory.decodeResource(getResources(),img[i]);
            }
            pikarunsp= new SpriteAnim(imge,resulusionx/9,(float) (resulusiony/1.8),(float)(resulusiony/4.32));
            principal= new CountDownTimer(1000,1) {
                @Override
                public void onTick(long millisUntilFinished) {
                        invalidate();
                }

                @Override
                public void onFinish() {
                    principal.start();

                }
            };principal.start();
            assets = new Objetos[10];
            for(int i=0;i< assets.length;i++)
            {
                assets[i]=new Objetos(getApplication(),pixelArt[0],resulusionx+300,(float) (resulusiony/1.5),resulusiony/8,"PIEDRA");
            }
            ObjetosEntregar=new CountDownTimer(10000,velocidadrelog) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(JUEGO){
                        if(posrocas<10)
                        {
                            assets[posrocas].setEstado(true);
                        }
                    }else
                    {
                        for(int i=0;i<assets.length;i++)
                        {
                            assets[i].setEstado(false);
                        }
                    }
                }
                @Override
                public void onFinish() {
                    ObjetosEntregar.start();
                }
            };
            ObjetosEntregar.start();
            puntuacionCont=new CountDownTimer(5000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(JUEGO) {
                        puntuacionGlobal++;
                        if(puntuacionGlobal==20)
                        {
                            velocidadrelog=1500;
                            velocidad=-20;
                        }
                        if(puntuacionGlobal==40)
                        {
                            velocidadrelog=1100;
                            velocidad=-30;
                        }
                        if(puntuacionGlobal==60)
                        {
                            velocidadrelog=800;
                            velocidad=-40;
                        }
                        if(puntuacionGlobal>100)
                        {
                            velocidad=-60;
                        }
                    }
                }
                @Override
                public void onFinish() {
                    puntuacionCont.start();
                }
            };
            puntuacionCont.start();
            global=new CountDownTimer(10000,1) {
                @Override
                public void onTick(long l) {
                    if(pikarunsp.anim)
                    {
                        /* MOVIMIENTO FONDO */
                        if( capas[1].x>=-(capas[1].getTamano()))
                        {
                            capas[1].moverX(-5);
                        }else
                        {
                            capas[1].setX(resulusionx);
                        }
                        if( capas[0].x>=-(capas[0].getTamano()))
                        {
                            capas[0].moverX(-5);
                        }else
                        {
                            capas[0].setX(resulusionx);
                        }
                        /* MOVIMIENTO PISO */
                        if( capas[2].x>=-(capas[2].getTamano()))
                        {
                            capas[2].moverX(-10);
                        }else
                        {
                            capas[2].setX(resulusionx);
                        }
                        if( capas[3].x>=-(capas[3].getTamano()))
                        {
                            capas[3].moverX(-10);
                        }else
                        {
                            capas[3].setX(resulusionx);
                        }
                        if(pikarunsp.getEstado())
                        {
                            if((pikarunsp.getPosinicialy()-(pikarunsp.getPosinicialy()/2) <=pikarunsp.y))
                            {
                                pikarunsp.y-=20;
                            }else
                            {

                                pikarunsp.estado=false;
                            }
                        }else
                        {
                            if(pikarunsp.getPosinicialy()>=pikarunsp.y){
                                pikarunsp.y+=20;
                            }else
                            {
                                pikarunsp.salto=true;
                            }
                        }
                    }
                }
                @Override
                public void onFinish() {
                    global.start();
                }
            };
            global.start();
            puntos= new Sprite(BitmapFactory.decodeResource(getResources(),R.drawable.puntos),0,-(resulusiony/30),(float)(resulusiony/2.5));
            gameover = new Sprite(BitmapFactory.decodeResource(getResources(),R.drawable.gameover),(float)(resulusionx/2-(resulusionx/2.8)),resulusiony/2-(resulusiony/4),(float)(resulusiony*1.2));
            reproductor= new Musica(getApplicationContext(),R.raw.run,true);
            reproductor.setVolumen((float)0.3);
            reproductor.reproducir();
            pikasonido = new Musica(getApplicationContext(),R.raw.pikaeffect,false);
            pikadeath= new Musica(getApplicationContext(),R.raw.pikadeath,false);
        }
        public void onDraw (Canvas c)
        {
            Paint p = new Paint();
            p.setStyle(Paint.Style.FILL);
            p.setColor(Color.rgb(59, 66, 167));
            c.drawPaint(p); //PARA DIBUJAR EL PAINT
            for(int i=0;i<capas.length;i++)
            {
                c.drawBitmap(capas[i].imagen, capas[i].x, capas[i].y, p);
            }
            for(int i=0;i<assets.length;i++)
            {
                assets[i].dibujarObjeto(c);
                assets[i].moverX(velocidad);
            }
            for (int i = 0; i < assets.length; i++) {
                if (assets[i].onColission(pikarunsp)) {
                    JUEGO=false;
                    assets[i].moverX(0);
                    System.out.println("CHOKO");
                    pikarunsp.animSTOP();
                }
            }
            pikarunsp.dibujar(c);
            c.drawBitmap(puntos.imagen, puntos.x,puntos.y, p);
            p.setColor(Color.BLACK);
            p.setTextSize(resulusionx/40);
            p.setStyle(Paint.Style.FILL);
            c.drawText("DISTANCIA: "+puntuacionGlobal,resulusionx/38,resulusiony/22,p);
            if(!JUEGO)
            {
                if(vibrar){
                    pikadeath.reproducir();
                    reproductor.pausar();
                    sistemavibrador.vibrate(500);
                    vibrar=false;
                }
                c.drawBitmap(gameover.imagen, gameover.x,gameover.y, p);
            }
        }
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
            }
            if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                if(!JUEGO)
                {
                    JUEGO=true;
                    puntuacionGlobal=0;
                    pikarunsp.animINI();
                    vibrar=true;
                    reproductor.reproducir();
                }else
                {
                    if(pikarunsp.getSalto())
                    {
                        pikarunsp.saltar(true);
                        pikasonido.reproducir();
                    }

                }
            }
            return true;
        }
    }
    public void Resolucion() {
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        resulusionx = size.x;
        resulusiony = size.y;
        System.out.println("RESOLUCION "+resulusionx+","+resulusiony);
    }
}